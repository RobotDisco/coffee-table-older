(ns coffee-table.components.sidebar
  (:require [om.core :as om]
            [om-semantic.rating :as r]
            [cljs.core.async :refer [put!]]
            [sablono.core :as html :refer-macros [html]]))


(defn visit-summary [visit owner opts]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitSummary")
    om/IRender
    (render [this]
      (let [channel (get-in opts [:channels :controls])
            selected-visit (:selected-visit visit)
            visit-id (:id visit)]
        (html [:div.item {:on-click #(put! channel [:visit-selected visit-id])}
               [:div.content
                [:div.header
                 (if (= selected-visit (:id visit))
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

(defn visit-list [data owner opts]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitList")
    om/IRender
    (render [_]
      (let [visits (:visits data)
            selected-visit (:selected-visit data)]
        (html [:div.ui.divided.items
               (om/build-all
                visit-summary
                visits
                {:opts {:channels (:channels opts)}
                 :fn #(merge {:selected-visit selected-visit} %)})])))))
