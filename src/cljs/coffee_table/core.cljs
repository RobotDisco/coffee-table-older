(ns ^:figwheel-always coffee-table.core
  (:require [om.core :as om]
            [coffee-table.state :refer [app-state]]
            [coffee-table.components.app :refer [app]]))

(enable-console-print!)

(defn main [target state]
  (om/root app state {:target target}))

(defn setup! []
  (main (. js/document (getElementById "app")) app-state))

(set! (.-onload js/window) setup!)
