(ns coffee-table.parser
  (:require [om.next.server :as om]))

(defmulti readf om/dispatch)

(defmethod readf :default
  [_ _ _]
  {:value :notfound})

(defmethod readf :app/visits
  [{:keys [state]} key _]
  (let [st @state]
    {:value (:app/visits st)}))
