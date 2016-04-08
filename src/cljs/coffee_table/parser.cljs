(ns coffee-table.parser
  (:require [om.next :as om]
            [cljs-time.format :refer [parse formatters]]))

(defmulti mutate om/dispatch)

(defmethod mutate 'visit/display
  [{:keys [state]} _ {:keys [id]}]
  {:action
   (fn []
     (let [{:keys [visits/list] :as st} @state
           visit (some #(when (= id (:id %)) %) list)]
       (swap! state assoc :buffer visit)))})

(defn get-visits [state]
  (into [] (get state :visits/list)))

(defmulti read om/dispatch)

(defmethod read :visits/list
  [{:keys [state]} _ _]
  (let [st @state]
    {:value (get-visits st)}))

(defmethod read :default
  [{:keys [state]} _ _]
  {:value "butt"})

(def parser (om/parser {:read read :mutate mutate}))
