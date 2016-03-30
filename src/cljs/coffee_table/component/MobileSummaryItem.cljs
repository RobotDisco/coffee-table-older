(ns coffee-table.component.MobileSummaryItem
  (:require-macros [devcards.core :as devcards :refer [defcard]])
  (:require [goog.dom :as gdom]
            [cljsjs.semantic-ui]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [sablono.core :as html :refer-macros [html]]
            [om-next-semantic.rating :refer [Rating]]
            [cljs-time.format :refer [unparse formatters]]))

(defui ^:once MobileSummaryItem
  Object
  (render [this]
          (let [props (om/props this)]
            (html [:div.ui.segment
                   [:div
                    [:i.coffee.icon]
                    [:span (props :name)]]
                   ((om/factory Rating) {:rating (props :beverage-rating)
                                         :max-rating 5})
                   [:div
                    [:i.calendar.icon]
                    [:span (unparse (formatters :date)
                                    (props :date))]]]))))

(defcard mobile-item
  ((om/factory MobileSummaryItem)
   {:name "butt"
    :beverage-rating 2
    :date (cljs-time.core.now)}))

(defcard mobile-item-2
  ((om/factory MobileSummaryItem)
   {:name "butt2"
    :beverage-rating 3
    :date (cljs-time.core.now)}))
