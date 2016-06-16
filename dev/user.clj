(ns user
  (:require [system.repl :refer [system init start stop go reset]]
            [coffee-table.systems :refer [dev-system]]
            [clojure.java.io :as io]
            [datomic.api :as datomic]))

(system.repl/set-init! #'dev-system)

(def schema
  (delay
   (read-string
    (slurp (io/resource "data/schema.edn")))))

(def seed
  (delay
   (read-string
    (slurp (io/resource "data/seed.edn")))))

(defn create-schema [conn]
  (datomic/transact conn @schema))

(defn create-seed [conn]
  (datomic/transact conn @seed))

(defn create-db []
  (let [uri  "datomic:mem://coffee-table"
        _    (datomic/create-database uri)
        conn (datomic/connect uri)]
    (create-schema conn)
    (create-seed conn)))
