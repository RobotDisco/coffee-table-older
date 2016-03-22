(ns coffee-table.component.MobileSummaryItem
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs-time.format :refer [unparse formatters]]))

(defui ^:once MobileSummaryItem
  Object
  (render [this]
          (dom/div nil
                   (dom/p nil (get (om/props this) :name))
                   (dom/p nil (get (om/props this) :beverage-rating))
                   (dom/p nil (unparse (formatters :date)
                                       (get (om/props this) :date))))))
