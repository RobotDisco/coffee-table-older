(ns coffee-table.systems
  (:require [system.core :refer [defsystem]]
            (system.components
             [jetty :refer [new-web-server]]
             #_ [repl-server :refer [new-repl-server]]
             #_ [cider-repl-server :refer [new-cider-repl-server]]
             #_ [datomic-server :refer [new-datomic-db]])
            [figwheel-sidecar.system :as figwheel]
            [environ.core :refer [env]]
            [coffee-table.handler :refer [dev-app app]]))

(defsystem dev-system
  [:web (new-web-server (Integer. (env :http-port)) dev-app)
   :figwheel (figwheel/figwheel-system (figwheel/fetch-config))
   #_ :cider #_ (new-cider-repl-server (Integer. (env :cider-port)))])
