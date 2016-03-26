(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [coffee-table.component.MobileSummaryList :refer [MobileSummaryList]]
            [coffee-table.component.MobileVisitView]
            [cljs-time.core :as time]))

(defonce app-state (atom {:visits [{:name "FKA Twigs Cafe"
                                    :date (time/now)
                                    :beverage-rating 3
                                    }
                                   {:name "Grimes Bistro"
                                    :date (time/now)
                                    :beverage-rating 4}
                                   {:name "Tango Palace"
                                    :date (time/now)
                                    :beverage-rating 5}]}))

(defonce reconciler (om/reconciler {:state app-state}))

(om/add-root! reconciler MobileSummaryList (gdom/getElement "app"))
