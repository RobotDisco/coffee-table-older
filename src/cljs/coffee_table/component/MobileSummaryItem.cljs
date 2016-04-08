(ns coffee-table.component.MobileSummaryItem
  (:require-macros [devcards.core :as devcards :refer [defcard]])
  (:require [goog.dom :as gdom]
            [cljsjs.semantic-ui]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [sablono.core :as html :refer-macros [html]]
            [om-next-semantic.rating :refer [Rating]]
            [cljs-time.format :refer [unparse formatters]]))

(defn handle-select [this id]
  (om/transact! this `[(visit/display {:id ~id})]))

(defui ^:once MobileSummaryItem
  static om/Ident
  (ident [this {:keys [id]}]
         [:visit/list id])
  static om/IQuery
  (query [this] [:id :name :date :beverage-rating])
  Object
  (render [this]
          (let [props (om/props this)
                {:keys [id name beverage-rating date]} props]
            (html
             [:div.ui.segment {:onClick #(handle-select this id)}
              [:div
               [:i.coffee.icon]
               [:span name]]
              ((om/factory Rating) {:rating beverage-rating
                                    :max-rating 5})
              [:div
               [:i.calendar.icon]
               [:span (unparse (formatters :date)
                               date)]]]))))

(def mobile-summary-item (om/factory MobileSummaryItem {:keyfn :id}))


(defcard mobile-item
  (mobile-summary-item
   {:id 1
    :name "butt"
    :beverage-rating 2
    :date (cljs-time.core.now)}))

(defcard mobile-item-2
  (mobile-summary-item
   {:id 2@
    :name "butt2"
    :beverage-rating 3
    :date (cljs-time.core.now)}))
