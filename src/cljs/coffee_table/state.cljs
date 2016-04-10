(ns coffee-table.state
  (:require [cljs-time.core :as time]))

(def visits [{:db/id 1
              :visit/name "FKA Twigs Cafe"
              :visit/date (time/now)
              :visit/address {:address/address1 "117 Grimes Boulevard"
                              :address/address2 "CPL 593H Suite"
                              :address/city "Toronto"
                              :address/region "Ontario"
                              :address/country "Canada"}
              :visit/espresso-machine "Elektra Micro Casa a Leva"
              :visit/grinder "Mazzer Stepless Doserless"
              :visit/roast "Intelligentsia Diablo"
              :visit/beverage-ordered "Single shot espresso"
              :visit/beverage-rating 4
              :visit/beverage-notes "Something something something"
              :visit/service-rating 3
              :visit/service-notes "Something something something"
              :visit/ambience-rating 5
              :visit/ambience-notes "Something something something"
              :visit/other-notes "Something something something"}])


(defonce app-state
  {:app/editing false
   :app/buffer {}
   :app/mode :list
   :app/visits visits})
