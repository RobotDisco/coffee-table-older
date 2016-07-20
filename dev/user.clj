(ns user
  (:require [system.repl :refer [system init start stop go reset]]
            [coffee-table.systems :refer [dev-system]]
            [clojure.java.io :as io]
            [coffee-table.datomic :refer [datomic-uri]]
            [datomic.api :as datomic]))

(system.repl/set-init! #'dev-system)

(def seed
  (delay
   (read-string
    (slurp (io/resource "data/seed.edn")))))

(defn seed-db []
  (let [conn (datomic/connect datomic-uri)]
    (datomic/transact conn @seed)))
