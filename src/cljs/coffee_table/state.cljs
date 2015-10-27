(ns coffee-table.state
  (:require [om.core :as om]
            [coffee-table.mock-data :as mock-data]))

(defonce app-state
  (atom mock-data/initial-state))

(defn current-visit []
  (om/ref-cursor (:current-visit (om/root-cursor app-state))))
