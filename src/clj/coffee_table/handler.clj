(ns coffee-table.handler
  (:require [coffee-table.parser :as parser]
            [coffee-table.state :as state]
            [bidi.bidi :as bidi]
            [om.next.server :as om]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.transit :refer [wrap-transit-body wrap-transit-response]]
            [cognitect.transit :as transit]
            [ring.util.response :as res]))


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

(defn query
  [{:keys [params body]}]
  (let [query-env {:state state/app-state}
        result ((om/parser {:read parser/readf}) query-env body)]
    {:status 200 :headers {"Content-Type" "application/transit+json"} :body result}))

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
