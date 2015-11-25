(ns coffee-table.visits
  (:require [cljs-time.core :as t]))

(def new-visit
  {:cafe-name ""
   :date-visited (t/date-time 1983 8 19)
   :beverage ""
   :beverage-rating {:rating 0
                     :max-rating 5}})
