(ns coffee-table.systems
  (:require [system.core :refer [defsystem]]
            (system.components
             [jetty :refer [new-web-server]]
             [repl-server :refer [new-repl-server]])
            [coffee-table.datomic :refer [datomic-uri new-datomic-database]]
            [figwheel-sidecar.system :as figwheel]
            [environ.core :refer [env]]
            [coffee-table.handler :refer [dev-app app]]))

(defsystem dev-system
  [:web (new-web-server (Integer. (env :http-port)) dev-app)
   :figwheel (figwheel/figwheel-system (figwheel/fetch-config))
   :datomic (new-datomic-database datomic-uri)])

(defsystem prod-system
  [:web (new-web-server (Integer. (env :http-port)) dev-app)
   :datomic (new-datomic-database datomic-uri)
   :repl (new-repl-server (Integer. (env :repl-port)))])
