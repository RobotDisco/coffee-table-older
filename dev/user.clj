(ns user
  (:require [system.repl :refer [system init start stop go reset]]
            [coffee-table.systems :refer [dev-system]]))

(system.repl/set-init! #'dev-system)
