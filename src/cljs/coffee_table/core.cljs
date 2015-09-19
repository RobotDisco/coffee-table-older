(ns coffee-table.core
  (:require [coffee-table.components.app :as app]
            [coffee-table.mock-data :as mock-data]
            [om.core :as om]
            [cljs.core.async :refer [chan]]))

(enable-console-print!)

(defonce controls-ch
  (chan))

(defonce app-state
  (atom (mock-data/initial-state {:controls controls-ch})))

(om/root app/app app-state
         {:target (. js/document (getElementById "app"))})
