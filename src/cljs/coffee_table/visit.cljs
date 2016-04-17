(ns coffee-table.visit
  (:require [cljs-time.core :as time]))

(defn new-visit []
  {:visit/name ""
   :visit/date (time/now)
   :visit/address {:address/address1 ""
                   :address/address2 ""
                   :address/city ""
                   :address/region ""
                   :address/country ""}
   :visit/espresso-machine ""
   :visit/grinder ""
   :visit/roast ""
   :visit/beverage-ordered ""
   :visit/beverage-rating 0
   :visit/beverage-notes ""
   :visit/service-rating 0
   :visit/service-notes ""
   :visit/ambience-rating 0
   :visit/ambience-notes ""
   :visit/other-notes ""})

(defn pending? [visit]
  (not (contains? visit :db/id)))
