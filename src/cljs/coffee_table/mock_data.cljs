(ns coffee-table.mock-data
  (:require [cljs-time.core :as t]))

(def initial-state
  (let [visits [{:id 1
                 :cafe-name "FKA Twigs Cafe"
                 :date-visited (t/date-time 2015 9 18)
                 :beverage "Cortado"
                 :beverage-rating {:rating 3
                                   :max-rating 5}}
                {:id 2
                 :cafe-name "Maman"
                 :date-visited (t/date-time 2015 9 10)
                 :beverage "Espresso, single shot"
                 :beverage-rating {:rating 4
                                   :max-rating 5}}
                {:id 3
                 :cafe-name "Dark Horse (Queen East)"
                 :date-visited (t/date-time 2011 11 5)
                 :beverage "Macchiato"
                 :beverage-rating {:rating 2
                                   :max-rating 5}}]]
        {:current-visit (first visits)
         :visits visits}))
