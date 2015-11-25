(ns coffee-table.state
  (:require [om.core :as om]
            [coffee-table.mock-data :as mock]))

(defn initial-state []
  (let [visits (mock/visit-list)]
    {:visits visits
     :current-visit (first visits)
     :main-window {:editing? false}}))


(defonce app-state
  (atom (initial-state)))

(defn current-visit []
  (om/ref-cursor (:current-visit (om/root-cursor app-state))))

(defn main-window []
  (om/ref-cursor (:main-window (om/root-cursor app-state))))
