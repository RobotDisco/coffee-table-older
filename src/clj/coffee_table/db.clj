(ns coffee-table.db
  (:require [ragtime.jdbc :as jdbc])
  (:require [ragtime.repl :as repl])
  (:require [ragtime.strategy :as strategy]))

(def db-config {:classname "org.postgresql.Driver"
                :subprotocol "postgresql"
                :subname "//db:5432/postgres"
                :user "postgres"})

(def migration-config
  {:datastore (jdbc/sql-database db-config)
   :migrations (jdbc/load-resources "migrations")
   :strategy strategy/rebase})

(defn migrate []
  (repl/migrate migration-config))

(defn rollback []
  (repl/rollback migration-config))
