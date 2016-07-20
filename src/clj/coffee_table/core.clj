(ns coffee-table.core
  (:require [coffee-table.systems :refer [prod-system]]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn -main
  "Start the application"
  []
  (alter-var-root #'prod-system component/start))
