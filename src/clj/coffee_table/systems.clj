(ns coffee-table.systems
  (:require [system.core :refer [defsystem]]
            (system.components
             [jetty :refer [new-web-server]]
             #_ [repl-server :refer [new-repl-server]]
             #_ [cider-repl-server :refer [new-cider-repl-server]]
             #_ [datomic-server :refer [new-datomic-db]])
            [environ.core :refer [env]]
            [coffee-table.handler :refer [handler]]))

(defsystem dev-system
  [:web (new-web-server (Integer. (env :http-port)) handler)
   #_ :cider #_ (new-cider-repl-server (Integer. (env :cider-port)))])
