(ns coffee-table.components.main-area
  (:require [om.core :as om]
            [om-semantic.rating :as r]
            [sablono.core :as html :refer-macros [html]]))


(defn visit-detail [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitDetail")
    om/IRender
    (render [this]
      (html [:form.ui.form
             [:div.field
              [:label "Cafe Name"]
              [:input.text {:type "text"
                            :name "cafe-name"
                            :value (:cafe_name data)}]]
             [:div.field
              [:label "Date Visited"]
              [:input {:type "date"
                       :name "date-visited"
                       :value (:date_visited data)}]]
             [:div.field
              [:label "Beverage Ordered"]
              [:input {:type "text"
                       :name "beverage"
                       :value (:beverage data)}]]
             [:div.field
              [:label "Beverage Rating"]
              (om/build r/rating
                        {:rating (:beverage_rating data)
                         :max-rating 5
                         :interactive false})]]))))
