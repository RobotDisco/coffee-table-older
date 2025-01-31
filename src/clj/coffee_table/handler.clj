(ns coffee-table.handler
  (:require [clojure.walk :as walk]
            [coffee-table.parser :as parser]
            [bidi.bidi :as bidi]
            [om.next.server :as om]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.transit :refer [wrap-transit-body wrap-transit-response]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]
            [cognitect.transit :as transit]
            [ring.util.response :as res]
            [clojure.walk :as walk]
            [datomic.api :as d]
            [om.next.impl.parser :as omp]
            [coffee-table.datomic :refer [datomic-uri]]))


(def joda-time-writer
  (transit/write-handler
   (constantly "m")
   (fn [v] (-> ^org.joda.time.ReadableInstant v .getMillis))
   (fn [v] (-> ^org.joda.time.ReadableInstant v .getMillis .toString))))

(def routes
  ["" {"/" :index
       "/query" {:get {[""] :query}
                 :post {[""] :query}}}])

(defn index
  [request]
  (assoc (res/resource-response (str "index.html") {:root "public"})
         :headers {"Content-Type" "text/html"}))

(defn mutation? [query]
  (some (comp symbol? :dispatch-key) (:children (omp/query->ast query))))

(defn query
  [{:keys [params body]}]
  (let [result ((om/parser {:read parser/readf :mutate parser/mutatef})
                {:conn (d/connect datomic-uri)} body)
        result' (if (mutation? body)
                  []
                  (walk/postwalk (fn [x]
                                   (if (and (sequential? x) (= :result (first x)))
                                     [(first x) (dissoc (second x) :db-before :db-after :tx-data)] x)) result))]
    {:status 200 :headers {"Content-Type" "application/transit+json"} :body result'}))

(defn handler [request]
  (let [match (bidi/match-route routes (:uri request)
                                :request-method (:request-method request))]
    (case (:handler match)
      :index (index request)
      :query (query request)
      request)))

(def app
  (-> handler
      wrap-transit-body
      (wrap-transit-response {:opts {:handlers {org.joda.time.DateTime joda-time-writer}}})
      (wrap-resource "public")))

(def dev-app
  (-> app
      wrap-stacktrace))
