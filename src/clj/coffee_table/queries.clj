(ns coffee-table.queries
  (:require [datomic.api :as d]))

(defn visits
  ([db]
   (visits db nil))
  ([db selector]
   (visits db selector nil))
  ([db selector _]
   (let [q (cond->
               '[:find [(pull ?eid selector) ...]
                 :in $ selector
                 :where
                 [?eid :visit/date]])]
     (d/q q db (or selector '[*])))))
