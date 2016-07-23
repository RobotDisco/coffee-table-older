(ns coffee-table.core
  (:gen-class)
  (:require [coffee-table.systems :refer [prod-system]]
            [system.repl :refer [set-init! start]]))

(defn -main
  "Start the application"
  []
  (set-init! #'prod-system)
  (start))
