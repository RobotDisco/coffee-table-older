(ns coffee-table.visits
  (require [coffee-table.db :refer [db-config]])
  (require [yesql.core :refer [defquery]]))

(defn json-value-reader [key value]
  (cond
    (= key :date_visited) (java.sql.Date/valueOf value)
    (= key :beverage_rating) (Integer/parseInt value)
    :else value))

(defquery list-visits "visits/list.sql" {:connection db-config})

(defquery create-visit<! "visits/create.sql" {:connection db-config})

(defquery retrieve-visit "visits/retrieve.sql" {:connection db-config})
