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
