(ns coffee-table.component.datomic
  (:require [datomic.api :as d]
            [com.stuartsierra.component :as component]
            [io.rkn.conformity :as c]
            [clojure.java.io :as io]))

(defn bootstrap!
  "Bootstrap schema into database."
  [uri]
  ;; Create the database
  (d/create-database uri)
  ;; Connect
  (let [conn (d/connect uri)]
    ;; and transact app schema...
    (doseq [resource ["db/000-initial-schema.edn"]]
      (let [norms (c/read-resource resource)]
        (c/ensure-conforms conn norms)))
    (d/release conn)))

(defrecord DatomicDatabase [uri]
  component/Lifecycle
  (start [component]
    (bootstrap! uri)))

(defn new-datomic-db [uri]
  (map->DatomicDatabase {:uri uri}))
