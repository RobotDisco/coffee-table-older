(ns coffee-table.components.app
  (:require [om.core :as om]
            [om.dom :as dom]
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
        (apply dom/div #js {:className "ui divided items"}
               (om/build-all visit-summary
                             visits
                             {:opts {:channels (:channels opts)}
                              :fn #(merge {:selected-visit selected-visit} %)}))))))

(defn visit-detail [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitDetail")
    om/IRender
    (render [this]
      (let [selected-visit (:selected-visit data)]
        (html [:form.ui.form
                    [:div.field
                     [:label "Cafe Name"]
                     [:input.text {:type "text"
                                   :name "cafe-name"
                                   :value (:cafe_name selected-visit)}]]
                    [:div.field
                     [:label "Date Visited"]
                     [:input {:type "date"
                              :name "date-visited"
                              :value (:date_visited selected-visit)}]]
                    [:div.field
                     [:label "Beverage Ordered"]
                     [:input {:type "text"
                              :name "beverage"
                              :value (:beverage selected-visit)}]]
                    [:div.field
                     [:label "Beverage Rating"]
                     (om/build r/rating
                               {:rating (:beverage_rating selected-visit)
                                :max-rating 5
                                :interactive false})]])))))

(defn app [app owner ops]
  (reify
    om/IDisplayName
    (display-name [_]
      "CoffeeTable")
    om/IRender
    (render [this]
      (let [visits (:visits app)
            selected-visit (first (filter #(= (:selected-visit app) (:id %))
                                          visits))]
        (html [:div.ui.grid
               [:div.four.wide.column
                [:button.ui.basic.button
                 [:i.plus.icon]
                 "Add Visit"]
                (om/build visit-list
                          (select-keys app [:visits :selected-visit])
                          {:opts {:channels (:channels app)}})]
               [:div.twelve.wide.column
                [:div.ui.basic.segment
                 (om/build visit-detail
                           {:selected-visit selected-visit}
                           {:opts {:channels (:channels app)}})]]])))))
