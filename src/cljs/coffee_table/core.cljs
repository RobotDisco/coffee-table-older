(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [coffee-table.state :as state]
            [coffee-table.parser :as parser]
            [coffee-table.component.App :refer [App]]
            [cljs-time.core :as time]))

(enable-console-print!)

(defonce reconciler (om/reconciler {:state state/app-state
                                    :parser parser/parser}))

(om/add-root! reconciler App (gdom/getElement "app"))
