(ns coffee-table.components.sidebar
  (:require [om.core :as om]
            [om-semantic.rating :as r]
            [sablono.core :as html :refer-macros [html]]
            [coffee-table.visits :as visits]
            [coffee-table.state :as state]))


(defn add-visit-button
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "AddVisitButton")
    om/IRender
    (render [this]
      (html [:button.ui.basic.button
             {:on-click #(om/update! (state/current-visit)
                                     visits/new-visit)}
             [:i.plus.icon]
             "Add Visit"]))))

(defn visit-summary [visit owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitSummary")
    om/IRender
    (render [this]
      (let [current-visit (om/observe owner (state/current-visit))]
        (html [:div.item {:on-click #(om/update! current-visit @visit)}
               [:div.content
                [:div.header
                 (if (= (:id current-visit) (:id visit))
                   [:i.pointing.right.icon])
                 (:cafe_name visit)]
                [:div.meta
                 [:span (clojure.string/join
                         (list "Visited: "
                               (:date_visited visit)))]]
                [:div.description
                 [:span "Beverage Rating: "
                  (om/build r/rating
                            {:rating (:beverage_rating visit)
                             :max-rating 5
                             :interactive false})]]]])))))

(defn visit-list [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitList")
    om/IRender
    (render [_]
      (let [visits (:visits data)]
        (html [:div
               (om/build add-visit-button {})
               [:div.ui.divided.items
                (om/build-all
                 visit-summary
                 visits
                 {:key :id})]])))))
