(ns coffee-table.system
  (:require [com.stuartsierra.component :as component]
            [coffee-table.component.datomic :refer [new-datomic-db]]
            [coffee-table.component.routes :refer [new-app-routes]]
            [coffee-table.component.server :refer [new-yada-webserver]]))

(defn dev-system
  "Development system"
  [config-options]
  (let [{:keys [db-uri]} config-options]
    (component/system-map
     :db (new-datomic-db db-uri)
     :routes (component/using (new-app-routes) [:db])
     :listener (component/using (new-yada-webserver) [:routes]))))
