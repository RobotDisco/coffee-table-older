(ns coffee-table.parser
  (:require [om.next.server :as om]
            [coffee-table.queries :as q]
            [datomic.api :as d]))

(defmulti readf om/dispatch)

(defmethod readf :default
  [_ k _]
  (println "default read " k)
  {:value {:error (str "No handler for read key " k)}})

(defmethod readf :app/visits
  [{:keys [conn query]} _ params]
  {:value (q/visits (d/db conn) #_ query '[*] params)})

(defmethod readf :visits/by-id
  [{:keys [conn query]} _ {:keys [id]}]
  {:value (d/pull @(d/sync conn) (or query '[*]) id)})

(defmulti mutatef om/dispatch)

(defmethod mutatef 'buffer/save
  [{:keys [state]} key {:keys [data]}]
  {:value {:keys [:app/visits]}
   :action (fn []
             (let [{:keys [db/id]} data]
               (swap! state assoc-in [:app/visits id] (dissoc data :db/id))))})

(defmethod mutatef 'visit/add
  [{:keys [state]} key {:keys [data]}]
  {:value {:keys [:app/visits]}
   :action (fn []
             (swap! state update :app/visits conj data))})

(defmethod mutatef 'visit/delete
  [{:keys [state]} key {:keys [db/id]}]
  {:value {:keys [:app/visits]}
   :action (fn []
             (let [{:keys [app/visits]} @state]
               (swap! state assoc :app/visits (vec (concat (subvec visits 0 id) (subvec visits (inc id)))))))})
