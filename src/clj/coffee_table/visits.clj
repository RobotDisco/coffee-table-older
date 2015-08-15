(ns coffee-table.visits
  (require [coffee-table.db :refer [db-config]])
  (require [yesql.core :refer [defqueries]]))

(defn json-value-reader [key value]
  (cond
    (= key :date_visited) (java.sql.Date/valueOf value)
    :else value))

(defqueries "visits.sql" {:connection db-config})
