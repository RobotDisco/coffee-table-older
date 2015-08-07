(ns coffee-table.visits
  (require [coffee-table.db :refer [db-config]])
  (require [yesql.core :refer [defquery]]))

(defquery list-visits "visits/list.sql" {:connection db-config})

(defquery create-visit<! "visits/create.sql" {:connection db-config})
