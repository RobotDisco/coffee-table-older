(ns coffee-table.state
  (:require [clj-time.core :as time]))

(def visits [])


(def app-state (atom {:app/visits visits}))
