(ns user
  (:require [reloaded.repl :refer [system init start stop go reset]]
            [coffee-table.systems :refer [dev-system]]))

(reloaded.repl/set-init! dev-system)
