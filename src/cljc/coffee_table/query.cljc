(ns coffee-table.query
  (:require [coffee-table.model :as m]
            [datomic.api :as d]
            [schema.core :as s]))

(s/defn summaries :- [m/Summary]
  "Fetch summaries of all café visits from DB"
  [db]
  (d/q '[:find
         [(pull ?e [:db/id :visit/name :visit/date :visit/beverage-rating]) ...]
         :where [?e :visit/name]] db))

(s/defn visit :- m/Visit
  "Grab visit object by entity ID"
  [db id :- s/Int]
  (d/pull db '[*] id))

(s/defn add-visit
  "Add a café into the system"
  [conn visit :- m/Visit]
  (let [res (d/transact conn visit)]
    (:db-after @res)))

(s/defn delete-entity
  "Delete an entity from the system"
  [conn eid]
  (let [res (d/transact conn [:db.fn/retractEntity eid])]
    (:db-after @res)))
