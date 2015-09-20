(ns coffee-table.controllers.controls)

(defmulti control-event
  (fn [target message args state] message))

(defmethod control-event :visit-selected
  [target message args state]
  (-> state
      (assoc :selected-visit args)))
