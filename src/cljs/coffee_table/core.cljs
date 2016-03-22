(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [coffee-table.component.MobileSummaryList :refer [MobileSummaryList]]
            [cljs-time.core :as time]))

(def app-state (atom {:visits [{:name "FKA Twigs Cafe"
                                :date (time/now)
                                :beverage-rating 3
                                }
                               {:name "Grimes Bistro"
                                :date (time/now)
                                :beverage-rating 4}
                               {:name "Tango Palace"
                                :date (time/now)
                                :beverage-rating 5}]}))

(def reconciler (om/reconciler {:state app-state}))

(om/add-root! reconciler MobileSummaryList (gdom/getElement "app"))
