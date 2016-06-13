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

(defn create-schema [conn]
  (datomic/transact conn @schema))
