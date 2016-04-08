(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [coffee-table.state :as state]
            [coffee-table.parser :as parser]
            [coffee-table.component.MobileSummaryList :refer [MobileSummaryList]]
            [coffee-table.component.MobileVisitView]
            [cljs-time.core :as time]))

(defonce reconciler (om/reconciler {:state state/app-state
                                    :parser parser/parser}))

(om/add-root! reconciler MobileSummaryList (gdom/getElement "app"))
