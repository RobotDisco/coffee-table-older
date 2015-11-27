(ns coffee-table.components.sidebar
  (:require [cljs.reader :as reader]
            [cljs-time.format :as tf]
            [goog.events :as events]
            [om.core :as om]
            [om-semantic.rating :as r]
            [sablono.core :as html :refer-macros [html]]
            [coffee-table.visits :as visits :refer [edn2json]]
            [coffee-table.state :as state])
  (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(def ^:private meths
  {:get "GET"
   :put "PUT"
   :post "POST"
   :delete "DELETE"})

(defn edn-xhr [{:keys [method url data on-complete]}]
  (let [xhr (XhrIo.)]
    (events/listen xhr goog.net.EventType.COMPLETE
                   (fn [e]
                     (on-complete (reader/read-string (.getResponseText xhr)))))
    (. xhr
       (send url (meths method) (when data (pr-str data))
             #js {"Content-Type" "application/edn"}))))

(defn add-visit-handler [_]
  (let [current-visit (state/current-visit)
        main-window (state/main-window)]
    (om/update! current-visit visits/new-visit)
    (om/update! main-window :editing? true)))

(defn select-visit-handler [visit]
  (let [current-visit (state/current-visit)
        main-window (state/main-window)]
    (om/update! current-visit visit)
    (om/update! main-window :editing? false)))

(defn add-visit-button
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "AddVisitButton")
    om/IRender
    (render [this]
      (html [:button.ui.secondary.button
             {:on-click add-visit-handler}
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
        (html [:div.item {:on-click #(select-visit-handler @visit)}
               [:div.content
                [:div.header
                 (if (= (:id current-visit) (:id visit))
                   [:i.pointing.right.icon])
                 (:cafe-name visit)]
                [:div.meta
                 [:span (clojure.string/join
                         (list "Visited: "
                               (tf/unparse (tf/formatters :date) (:date-visited visit))))]]
                [:div.description
                 [:span "Beverage Rating: "
                  (om/build r/rating (:beverage-rating visit))]]]])))))

(defn visit-list [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "VisitList")
    om/IWillMount
    (will-mount [_]
      (edn-xhr {:method :get
                :url "/visits"
                :on-complete #(om/transact! data :visits (fn [_]
                                                           (mapv edn2json %)))}))
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
