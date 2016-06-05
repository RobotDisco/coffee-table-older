(ns coffee-table.parser
  (:require [om.next.server :as om]))

(defmulti readf om/dispatch)

(defmethod readf :default
  [_ _ _]
  {:value :notfound})

(defmethod readf :app/visits
  [{:keys [state]} key _]
  (let [st @state
        fn (fn [idx item]
             (assoc item :db/id idx))]
    {:value (into [] (map-indexed fn (:app/visits st)))}))

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
