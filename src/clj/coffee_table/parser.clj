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
  [{:keys [conn]} key {:keys [data]}]
  {:value {:keys [[:app/visits] [:visits/by-id (:db/id data)]]}
   :action (fn []
             @(d/transact conn [data]))})

(defmethod mutatef 'visit/add
  [{:keys [conn]} key {:keys [data]}]
  {:value {:keys [:app/visits]}
   :action (fn []
             @(d/transact conn [(merge
                                 {:db/id (d/tempid -1)}
                                 data)]))})

(defmethod mutatef 'visit/delete
  [{:keys [conn]} key {:keys [db/id]}]
  {:value {:keys [:app/visits [:visits/by-id id]]}
   :action (fn []
             @(d/transact conn [[:db.fn/retractEntity id]]))})
