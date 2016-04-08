(ns coffee-table.component.MobileSummaryList
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [coffee-table.component.MobileSummaryItem :as summary]))

(defui ^:once MobileSummaryList
  static om/IQuery
  (query [this] [{:visits/list (om/get-query summary/MobileSummaryItem)}])
  Object
  (render [this]
          (let [widget summary/mobile-summary-item
                props (om/props this)
                {:keys [visits/list]} props]
            (apply dom/div nil
                   (map widget list)))))
