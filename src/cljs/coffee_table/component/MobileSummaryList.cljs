(ns coffee-table.component.MobileSummaryList
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [coffee-table.component.MobileSummaryItem :as summary]))

(defui ^:once MobileSummaryList
  Object
  (render [this]
          (let [widget summary/mobile-summary-item
                visits (om/props this)]
            (apply dom/div nil
                   (map widget visits)))))

(def summary-list
  (om/factory MobileSummaryList))
