(ns coffee-table.controllers.controls
  (:require [coffee-table.visits :as visits]))

(defmulti control-event
  (fn [target message args state] message))

(defmethod control-event :visit-selected
  [target message args state]
  (-> state
      (assoc :current-visit args)))

(defmethod control-event :add-visit
  [target message args state]
  (let [visit visits/new-visit]
    (assoc state :current-visit visit)))
