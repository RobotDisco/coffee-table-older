(ns coffee-table.state
  (:require [cljs-time.core :as time]))

(def visits [{:id 1
              :name "FKA Twigs Cafe"
              :date (time/now)
              :address {:address1 "117 Grimes Boulevard"
                        :address2 "CPL 593H Suite"
                        :city "Toronto"
                        :region "Ontario"
                        :country "Canada"}
              :espresso-machine "Elektra Micro Casa a Leva"
              :grinder "Mazzer Stepless Doserless"
              :roast "Intelligentsia Diablo"
              :beverage-ordered "Single shot espresso"
              :beverage-rating 4
              :beverage-notes "Something something something"
              :service-rating 3
              :service-notes "Something something something"
              :ambience-rating 5
              :ambience-notes "Something something something"
              :other-notes "Something something something"}
             {:id 2
              :name "FKA Twigs Cafe"
              :date (time/now)
              :address {:address1 "117 Grimes Boulevard"
                        :address2 "CPL 593H Suite"
                        :city "Toronto"
                        :region "Ontario"
                        :country "Canada"}
              :espresso-machine "Elektra Micro Casa a Leva"
              :grinder "Mazzer Stepless Doserless"
              :roast "Intelligentsia Diablo"
              :beverage-ordered "Single shot espresso"
              :beverage-rating 4
              :beverage-notes "Something something something"
              :service-rating 3
              :service-notes "Something something something"
              :ambience-rating 5
              :ambience-notes "Something something something"
              :other-notes "Something something something"}
             {:id 3
              :name "FKA Twigs Cafe"
              :date (time/now)
              :address {:address1 "117 Grimes Boulevard"
                        :address2 "CPL 593H Suite"
                        :city "Toronto"
                        :region "Ontario"
                        :country "Canada"}
              :espresso-machine "Elektra Micro Casa a Leva"
              :grinder "Mazzer Stepless Doserless"
              :roast "Intelligentsia Diablo"
              :beverage-ordered "Single shot espresso"
              :beverage-rating 4
              :beverage-notes "Something something something"
              :service-rating 3
              :service-notes "Something something something"
              :ambience-rating 5
              :ambience-notes "Something something something"
              :other-notes "Something something something"}])


(defonce app-state
  (atom {:editing false
         :buffer nil
         :visits/list visits}))
