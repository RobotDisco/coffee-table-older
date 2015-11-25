(ns coffee-table.components.main-area
  (:require [cljs.core.async :refer [chan put! <!]]
            [cljs-time.format :as tf]
            [om.core :as om]
            [om-semantic.rating :as r]
            [sablono.core :as html :refer-macros [html]]
            [coffee-table.state :as state])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defn add-buttons [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "AddButtons")
    om/IRender
    (render [_]
      (html [:div
             [:button.ui.button.right.floated.primary
              {:type "button"}
              "Add"]]))))

(defn edit-buttons [{:keys [editing?] :as data} owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "EditButtons")
    om/IRender
    (render [_]
      (html [:div
             [:button.ui.button.left.floated.negative
              {:type "button"}
              "Delete"]
             (if editing?
               [:div.ui.buttons.right.floated
                [:button.ui.button
                 {:type "button"
                  :on-click #(om/update! data :editing? false)}
                 "Cancel"]
                [:div.or]
                [:button.ui.primary.button
                 {:type "button"}
                 "Save"]]
               [:button.ui.button.right.floated
                {:type "button"
                 :on-click #(om/update! data :editing? true)}
                "Edit"])]))))

(defn handle-change [e data edit-key owner f]
  (om/transact! data edit-key (fn [] (f (.. e -target -value)))))

(defn visit-detail [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitDetail")
    om/IRender
    (render [_]
      (let [main-window-c (om/observe owner (state/main-window))
            editing? (:editing? main-window-c)]
        (html [:form.ui.form
               [:div.field
                [:label "Cafe Name"]
                (if editing?
                  [:input.text {:type "text"
                                :name "cafe-name"
                                :value (:cafe-name data)
                                :on-change #(handle-change % data :cafe-name owner identity)}]
                  [:p (:cafe_name data)])]
               [:div.field
                [:label "Date Visited"]
                (if editing?
                  [:input {:type "date"
                           :name "date-visited"
                           :value (tf/unparse (tf/formatters :date) (:date-visited data))
                           :on-change #(handle-change % data :date-visited owner (partial tf/parse (tf/formatters :date)))}]
                  [:p (:cafe_name data)])]
               [:div.field
                [:label "Beverage Ordered"]
                (if editing?
                  [:input {:type "text"
                           :name "beverage"
                           :value (:beverage data)
                           :on-change #(handle-change % data :beverage owner identity)}]
                  [:p (:beverage data)])]
               [:div.field
                [:label "Beverage Rating"]
                (om/build r/rating (:beverage-rating data) {:fn #(assoc % :interactive editing?)})]
               (if (nil? (:id data))
                 (om/build add-buttons main-window-c)
                 (om/build edit-buttons main-window-c))])))))
