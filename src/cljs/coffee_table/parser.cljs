(ns coffee-table.parser
  (:require [om.next :as om]
            [cljs-time.format :refer [parse formatters]]
            [coffee-table.visit :as visit]))

(defmulti mutate om/dispatch)

(defmethod mutate 'visit/new
  [{:keys [state]} _ _]
  {:value {:keys [:app/buffer]}
   :action (fn []
             (swap! state assoc :app/buffer (visit/new-visit))
             (swap! state assoc :app/mode :visit)
             (swap! state assoc :app/editing true))})

(defmethod mutate 'app/list-mode
  [{:keys [state]} _ _]
  {:value {:keys [:app/editing :app/mode]}
   :action (fn []
             (let [st @state]
               (swap! state assoc :app/editing false)
               (swap! state assoc :app/mode :list)))})

(defmethod mutate 'visit/display
  [{:keys [state]} _ {:keys [id]}]
  {:value {:keys [:app/buffer :app/mode]}
   :action
   (fn []
     (let [{:keys [app/visits] :as st} @state
           new-buffer (into {} (get-in st [:visits/by-id id]))]
       (swap! state assoc :app/buffer new-buffer)
       (swap! state assoc :app/mode :visit)))})

(defmethod mutate 'buffer/save
  [{:keys [ast state]} _ _]
  (let [st @state
        {:keys [app/buffer app/editing visits/by-id]} st
        {:keys [db/id]} buffer]
    {:value {:keys [:app/editing [:visits/by-id id]]}
     :remote (assoc ast :params {:data buffer})
     :action (fn []
               (swap! state assoc-in [:visits/by-id id] buffer)
               (swap! state assoc :app/editing false)
               (swap! state assoc :app/mode :list))}))

(defmethod mutate 'visit/add
  [{:keys [state ast]} _ _]
  (let [st @state
        {:keys [app/buffer]} st]
    {:value {:keys [:app/visits :app/mode :app/editing]}
     :remote (assoc ast :params {:data buffer})
     :action (fn []
               (swap! state assoc :app/editing false)
               (swap! state assoc :app/mode :list))}))

(defmethod mutate 'buffer/revert
  [{:keys [state]} _ _]
  (let [st @state
        {:keys [:app/buffer :app/editing :visits/by-id]} st
        {:keys [db/id]} buffer]
    {:value {:keys [:app/editing :app/buffer]}
     :action (fn []
               (swap! state assoc :app/buffer (by-id 1))
               (swap! state assoc :app/editing false))}))

(defmethod mutate 'buffer/edit
  [{:keys [state]} _ _]
  {:value {:keys [:app/editing]}
   :action #(swap! state assoc :app/editing true)})

(defmethod mutate 'visit/delete
  [{:keys [state ast]} _ _]
  (let [st @state
        {:keys [:app/buffer]} st
        {:keys [db/id]} buffer
        remove-fn #(= [:visits/by-id id] %)]
    {:value {:keys [:app/visits :app/by-id :app/editing :app/mode]}
     :remote (assoc ast :params {:db/id id})
     :action (fn []
               (swap! state update :app/visits (fn [x] (into [] (remove remove-fn x))))
               (swap! state assoc :app/editing false)
               (swap! state assoc :app/mode :list))}))

(defmethod mutate 'edit-field
  [{:keys [state]} _ {:keys [key value]}]
  {:value {:keys [:app/buffer]}
   :action #(swap! state assoc-in (vec (flatten [:app/buffer key])) value)})

(defmethod mutate 'edit-date-field
  [{:keys [state]} _ {:keys [key value]}]
  {:value {:keys [:app/buffer]}
   :action #(swap! state assoc-in [:app/buffer key] (parse (formatters :date) value))})

(defmulti read om/dispatch)

(defmethod read :app/mode
  [{:keys [state]} key _]
  (let [st @state]
    {:value (get st key)}))

(defmethod read :app/editing
  [{:keys [state]} key _]
  (let [st @state]
    {:value (get st key)}))

(defmethod read :app/buffer
  [{:keys [query state]} key _]
  (let [st @state]
    {:value (get st key)}))

(defmethod read :app/visits
  [{:keys [query state]} key _]
  (let [st @state]
    {:value (om/db->tree query (get st key) st)
     :remote true}))

(defmethod read :visits/by-id
  [{:keys [query]} key _]
  {:remote true})

(defmethod read :default
  [{:keys [query state]} key _]
  (let [st @state]
    {:value (om/db->tree query (get st key) st)}))

(def parser (om/parser {:read read :mutate mutate}))
