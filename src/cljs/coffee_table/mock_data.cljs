(ns coffee-table.mock-data)

(defn initial-state [channels]
  (let [visits [{:id 1
                 :cafe_name "FKA Twigs Cafe"
                 :date_visited "2015-09-18"
                 :beverage "Cortado"
                 :beverage_rating 3}
                {:id 2
                 :cafe_name "Maman"
                 :date_visited "2015-09-10"
                 :beverage "Espresso, single shot"
                 :beverage_rating 4}
                {:id 3
                 :cafe_name "Dark Horse (Queen East)"
                 :date_visited "2011-11-05"
                 :beverage "Macchiato"
                 :beverage_rating 2}]]
        {:current-visit (first visits)
         :visits visits
         :channels channels}))
