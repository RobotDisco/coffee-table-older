(ns coffee-table.component.MobileSummaryList
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [coffee-table.component.MobileSummaryItem :as summary]))

(defui ^:once MobileSummaryList
  Object
  (render [this]
          (apply dom/div nil
                 (map (om/factory summary/MobileSummaryItem) ((om/props this) :visits)))))
