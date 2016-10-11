(ns coffee-table.parser
  (:require [om.next.server :as om]
            [datomic.api :as d]))

;;; Reader queries
(defmulti readf om/dispatch)

(defmethod readf :visit/all
  [{:keys [conn]} _ _]
  (let [db (d/db conn)
        res (d/q '[:find (pull ?e ?subquery)
                   :in $ ?subquery
                   :where [?e :visit/name]]
                 db '[*])]
    {:value res}))

(defmethod readf :visit/by-id
  [{:keys [conn]} _ {:keys [id]}]
  (let [db (d/db conn)
        res (d/q '[:find (pull ?e [*])
                   :in $ ?id
                   :where [?e :db/id ?id] [?e :visit/name]] db id)]
    {:value res}))


;;; Mutating queries
(defmulti mutatef om/dispatch)

(defmethod mutatef 'visit-add
  [{:keys [conn]} _ {:keys [visit]}]
  {:action (fn [] @(d/transact conn visit))})

(defmethod mutatef 'visit-delete
  [{:keys [conn]} _ {:keys [id]}]
  {:action (fn []
             @(d/transact conn [:db.fn/retractEntity id]))})
