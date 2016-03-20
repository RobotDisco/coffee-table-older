(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [coffee-table.component.MobileSummaryItem :refer [MobileSummaryItem]]
            [cljs-time.core :as time]))

(def msi (om/factory MobileSummaryItem))

(js/ReactDOM.render (msi) (gdom/getElement "app"))
