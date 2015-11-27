(ns coffee-table.core
  (:require [clojure.edn :as edn]
            [compojure.core :refer [defroutes GET PUT]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [datomic.api :as d]
            [ring.util.response :refer [file-response]]))

(def uri "datomic:free://localhost:4334/coffee-table")
(def conn (d/connect uri))

(defn index []
  (file-response "public/html/index.html" {:root "resources"}))

(defn generate-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(defn update-visit [id params]
  (generate-response {:status :ok}))

(defn visits []
  (let [db (d/db conn)
        visits (vec (map #(d/touch (d/entity db (first %)))
                         (d/q '[:find ?e :where [?e :visit/cafe-name]] db)))]
    (generate-response visits)))

(defroutes routes
  (GET "/" [] (index))
  (GET "/visits" [] (visits))
  (PUT "/visits/:id"
       {params :params edn-body :edn-body}
       (update-visit (:id params) edn-body))
  (route/files "/" {:root "resources/public"}))

(defn read-inputstream-edn [input]
  (edn/read {:eof nil}
            (java.io.PushbackReader.
             (java.io.InputStreamReader. input "UTF-8"))))

(defn parse-edn-body [handler]
  (fn [request]
    (handler (if-let [body (:body request)]
               (assoc request
                      :edn-body (read-inputstream-edn body))
               request))))

(def handler
  (-> routes
      parse-edn-body))
