(ns coffee-table.core
  (:require [clojure.edn :as edn]
            [compojure.core :refer [defroutes GET PUT DELETE POST]]
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

(defn delete-visit [id]
  @(d/transact conn [[:db.fn/retractEntity (Long.xb id)]])
  (generate-response {:status :ok}))

(defn update-visit [id params]
  @(d/transact conn [params])
  (generate-response {:status :ok}))

(defn add-visit [visit]
  (let [temp-id #db/id[:db.part/user]
        visit-entity (assoc visit :db/id temp-id)
        tx @(d/transact conn [visit-entity])
        db-after (:db-after tx)
        tempids (:tempids tx)]
    (generate-response {:status :ok :id (d/resolve-tempid db-after tempids temp-id)})))

(defn visits []
  (let [db (d/db conn)
        visits (vec (map #(d/touch (d/entity db (first %)))
                         (d/q '[:find ?e :where [?e :visit/cafe-name]] db)))]
    (generate-response visits)))

(defroutes routes
  (GET "/" [] (index))
  (GET "/visits" [] (visits))
  (POST "/visits" {edn-body :edn-body} (add-visit edn-body))
  (PUT "/visits/:id"
       {params :params edn-body :edn-body}
       (update-visit (:id params) edn-body))
  (DELETE "/visits/:id" {params :params} (delete-visit (:id params)))
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
