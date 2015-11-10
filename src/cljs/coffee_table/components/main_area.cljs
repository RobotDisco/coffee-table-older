(ns coffee-table.components.main-area
  (:require [cljs.core.async :refer [chan put! <!]]
            [om.core :as om]
            [om-semantic.rating :as r]
            [sablono.core :as html :refer-macros [html]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defn button-bar [data owner opts]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitEditorButtons")
    om/IRender
    (render [_]
      (if (= (:id data) nil)
        (html [:div
               [:button.ui.button.right.floated.primary
                {:type "button"}
                "Add"]])
        (html [:div
               [:button.ui.button.left.floated.negative
                {:type "button"}
                "Delete"]
               (if (:editing data)
                 [:div.ui.buttons.right.floated
                  [:button.ui.button
                   {:type "button"
                    :on-click #(put! (:chan opts) :stop-editing)}
                   "Cancel"]
                  [:div.or]
                  [:button.ui.primary.button
                   {:type "button"}
                   "Save"]]
                 [:button.ui.button.right.floated
                  {:type "button"
                   :on-click #(put! (:chan opts) :start-editing)}
                  "Edit"])])))))


(defn visit-detail [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitDetail")
    om/IWillMount
    (will-mount [_]
      (let [edit-chan (om/get-state owner :chan)]
        (go-loop [] (let [event (<! edit-chan)]
              (if (= event :start-editing)
                (om/set-state! owner :editing? true)
                (om/set-state! owner :editing? false))
              (recur)))))
    om/IInitState
    (init-state [_]
      {:chan (chan)
       :editing? false})
    om/IRenderState
    (render-state [_ state]
      (let [editing? (:editing? state)]
        (html [:form.ui.form
               [:div.field
                [:label "Cafe Name"]
                (if editing?
                  [:input.text {:type "text"
                                :name "cafe-name"
                                :value (:cafe_name data)}]
                  [:p (:cafe_name data)])]
               [:div.field
                [:label "Date Visited"]
                (if editing?
                  [:input {:type "date"
                           :name "date-visited"
                           :value (:date_visited data)}]
                  [:p (:cafe_name data)])]
               [:div.field
                [:label "Beverage Ordered"]
                (if editing?
                  [:input {:type "text"
                           :name "beverage"
                           :value (:beverage data)}]
                  [:p (:beverage data)])]
               [:div.field
                [:label "Beverage Rating"]
                (om/build r/rating
                          {:rating (:beverage_rating data)
                           :max-rating 5
                           :interactive editing?})]
               (om/build button-bar
                         {:id (:id data)
                          :editing editing?}
                         {:opts {:chan (:chan state)}})])))))
