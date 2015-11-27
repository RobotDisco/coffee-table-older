(ns coffee-table.visits
  (:require [cljs-time.core :as t]
            [cljs-time.coerce :as tc]))

(def new-visit
  {:cafe-name ""
   :date-visited (t/date-time 1983 8 19)
   :beverage ""
   :beverage-rating {:rating 0
                     :max-rating 5}})

(defn edn2json [edn]
  {:id (:db/id edn)
   :cafe-name (:visit/cafe-name edn)
   :date-visited (tc/from-date (:visit/date-visited edn))
   :beverage (:visit/beverage edn)
   :beverage-rating {:rating (:visit/beverage-rating edn)
                     :max-rating 5}})
