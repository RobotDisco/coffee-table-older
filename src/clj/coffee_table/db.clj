(ns coffee-table.db
  (:require [ragtime.jdbc :as jdbc])
  (:require [ragtime.repl :as repl])
  (:require [ragtime.strategy :as strategy]))

(def config
  {:datastore (jdbc/sql-database {:connection-uri
                             "jdbc:postgresql://db/postgres?user=postgres"})
   :migrations (jdbc/load-resources "migrations")
   :strategy strategy/rebase})

(defn migrate []
  (repl/migrate config))

(defn rollback []
  (repl/rollback config))
