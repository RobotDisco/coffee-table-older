(ns coffee-table.core
  (:require [coffee-table.components.app :as app]
            [coffee-table.mock-data :as mock-data]
            [om.core :as om]))

(enable-console-print!)

(defonce app-state
  (atom mock-data/initial-state))

(defn current-visit []
  (om/ref-cursor (:current-visit (om/root-cursor app-state))))

(defn main [target state]
  (om/root app/app app-state {:target target}))

(defn setup! []
  (main (. js/document (getElementById "app")) app-state))

(set! (.-onload js/window) setup!)
