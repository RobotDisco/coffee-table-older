(ns coffee-table.components.app
  (:require [om.core :as om]
            [om.dom :as dom]
            [om-semantic.rating :as r]
            [cljs.core.async :refer [put!]]))

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
        (dom/div #js {:className "item"
                      :onClick #(put! channel [:visit-selected visit-id])}
                 (dom/div #js {:className "content"}
                          (dom/div #js {:className "header"}
                                   (if (= selected-visit (:id visit))
                                     (dom/i #js {:className "pointing right icon"}))
                                   (:cafe_name visit))
                          (dom/div #js {:className "meta"}
                                   (dom/span nil (clojure.string/join
                                                  (list "Visited: "
                                                        (:date_visited visit)))))
                          (dom/div #js {:className "description"}
                                   (dom/span nil "Beverage Rating: ")
                                   (om/build r/rating
                                             {:rating (:beverage_rating visit)}))))))))

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
        (dom/form #js {:className "ui form"}
                  (dom/div #js {:className "field"}
                           (dom/label nil "Cafe Name")
                           (dom/input #js {:type "text"
                                           :name "cafe-name"
                                           :value (:cafe_name selected-visit)}))
                  (dom/div #js {:className "field"}
                           (dom/label nil "Date Visited")
                           (dom/input #js {:type "date"
                                           :name "date-visited"
                                           :value (:date_visited selected-visit)}))
                  (dom/div #js {:className "field"}
                           (dom/label nil "Beverage Ordered")
                           (dom/input #js {:type "text"
                                           :name "beverage"
                                           :value (:beverage selected-visit)}))
                  (dom/div #js {:className "field"}
                           (dom/label nil "Beverage Rating")
                           (om/build r/rating
                                     {:rating (:beverage_rating selected-visit)})))))))

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
        (dom/div #js {:className "ui grid"}
                 (dom/div #js {:className "four wide column"}
                          (om/build visit-list
                                    (select-keys app [:visits :selected-visit])
                                    {:opts {:channels (:channels app)}}))
                 (dom/div #js {:className "twelve wide column"}
                          (dom/div #js {:className "ui basic segment"}
                                   (om/build visit-detail
                                             {:selected-visit selected-visit}
                                             {:opts {:channels (:channels app)}}))))))))
