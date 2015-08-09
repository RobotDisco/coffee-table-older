(ns coffee-table.visits
  (require [coffee-table.db :refer [db-config]])
  (require [yesql.core :refer [defquery]]))

(defn json-value-reader [key value]
  (cond
    (= key :date_visited) (java.sql.Date/valueOf value)
    :else value))

(defquery list-visits "visits/list.sql" {:connection db-config})

(defquery create-visit<! "visits/create.sql" {:connection db-config})

(defquery retrieve-visit "visits/retrieve.sql" {:connection db-config})

(defquery update-visit! "visits/update.sql" {:connection db-config})

(defquery get-max-id "visits/get-max-id.sql" {:connection db-config})
