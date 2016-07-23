(ns coffee-table.datomic
  (:require [datomic.api :as d]
            [com.stuartsierra.component :as component]
            [io.rkn.conformity :as c]
            [environ.core :refer [env]])
  (:import datomic.Util))

(def datomic-uri (env :datomic-uri))

(defrecord DatomicDatabase [uri]
  component/Lifecycle
  (start [component]
    (d/create-database uri)
    (let [conn (d/connect uri)
          norms (c/read-resource "data/000-initial-schema.edn")]
      (c/ensure-conforms conn norms)
      (d/release conn)
      #_ (assoc component :conn conn)))
  (stop [component]
    (when (:conn component) (d/release (:conn component)))
    #_ (dissoc component :conn)))

(defn new-datomic-database [db-uri]
  (DatomicDatabase. db-uri))
