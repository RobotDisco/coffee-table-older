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

(defmethod mutate 'visit/add
  [{:keys [state]} _ _]
  {:value {:keys [:app/visits :app/mode :app/editing]}
   :action (fn []
             (let [st @state
                   {:keys [visits/by-id app/visits app/buffer]} @state
                   new-id (inc (count (keys by-id)))
                   buffer-with-id (assoc buffer :db/id new-id)
                   new-by-id (assoc by-id (:db/id buffer-with-id) buffer-with-id)
                   new-visits (conj visits [:visits/by-id new-id])]
               (swap! state assoc :visits/by-id new-by-id)
               (swap! state assoc :app/visits new-visits)
               (swap! state assoc :app/editing false)
               (swap! state assoc :app/mode :list)))})

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
  [{:keys [state]} _ _]
  (let [st @state
        {:keys [:app/buffer]} st
        {:keys [db/id]} buffer
        remove-fn #(= [:visits/by-id id] %)]
    {:value {:keys [:app/visits :app/by-id :app/editing :app/mode]}
     :action (fn []
               (swap! state update :app/visits (fn [x] (into [] (remove remove-fn x))))
               (swap! state assoc :app/editing false)
               (swap! state assoc :app/mode :list))}))

(defmethod mutate 'edit-field
  [{:keys [state]} _ {:keys [key value]}]
  {:value {:keys [:app/buffer]}
   :action #(swap! state assoc-in [:app/buffer key] value)})

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

(defmethod read :default
  [{:keys [query state]} key _]
  (let [st @state]
    {:value (om/db->tree query (get st key) st)}))

(def parser (om/parser {:read read :mutate mutate}))
