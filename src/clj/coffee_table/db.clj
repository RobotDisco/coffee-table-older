(ns coffee-table.db
  (:require [datomic.api :as d]
            [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import datomic.Util))

(def uri "datomic:free://localhost:4334/coffee-table")

(defn read-all [f]
  (Util/readAll (io/reader f)))

(defn transact-all [conn f]
  (doseq [txd (read-all f)]
    (d/transact conn txd))
  :done)

(defn create-db []
  (d/create-database uri))

(defn get-conn []
  (d/connect uri))

(defn load-schema []
  (transact-all (get-conn) (io/resource "data/schema.edn")))

(defn load-initial-data []
  (transact-all (get-conn) (io/resource "data/initial.edn")))

(defn init-db []
  (create-db)
  (load-schema)
  (load-initial-data))
