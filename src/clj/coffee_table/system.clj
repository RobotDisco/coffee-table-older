(ns coffee-table.system
  (:require [com.stuartsierra.component :as component]
            [coffee-table.component.datomic :refer [new-datomic-db]]))

(defn dev-system
  "Development system"
  [config-options]
  (let [{:keys [db-uri]} config-options]
    (component/system-map
     :db (new-datomic-db db-uri))))
