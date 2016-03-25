(ns coffee-table.component.MobileSummaryItem
  (:require-macros [devcards.core :as devcards :refer [defcard]])
  (:require [goog.dom :as gdom]
            [cljsjs.semantic-ui]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.format :refer [unparse formatters]]
            [devcards-om-next.core :as don :refer-macros [om-next-root defcard-om-next]]))

(defui ^:once MobileSummaryItem
  Object
  (componentDidMount [this]
                     (.rating (js/$ ".rating")))
  (componentDidUpdate [this prevprops prevstate]
                      (let [props (om/props this)
                            rating (props :beverage-rating)]
                        (.rating (js/$ ".rating") "set rating" rating)))
  (render [this]
          (html [:div.ui.segment
                 [:div
                  [:i.coffee.icon]
                  [:span (get (om/props this) :name)]]
                 [:div.ui.rating {:data-rating (get (om/props this) :beverage-rating)
                                  :data-max-rating 5}]
                 [:div [:i.calendar.icon] [:span (unparse (formatters :date)
                                                          (get (om/props this) :date))]]])))

(defcard mobile-item
  ((om/factory MobileSummaryItem)
   {:name "butt"
    :beverage-rating 2
    :date (cljs-time.core.now)}))
