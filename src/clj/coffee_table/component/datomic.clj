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
    conn))

(defrecord DatomicDatabase [uri]
  component/Lifecycle
  (start [component]
    (assoc component :conn (bootstrap! uri)))
  (stop [component]
    (d/release (get component :conn))
    (dissoc component :conn)))

(defn new-datomic-db [uri]
  (map->DatomicDatabase {:uri uri}))

(defn seed-db!
  "Seed the database with pre-baked data"
  [component]
  (let [{:keys [conn]} component
        data (io/reader (io/resource "db/seed.edn"))]
    (doseq [datom (datomic.Util/readAll data)]
      @(d/transact conn datom))
    (d/db conn)))

(defn get-db [datomic]
  (d/db (:conn datomic)))
