(ns coffee-table.component.routes
  (:require [coffee-table.parser :refer [mutatef readf]]
            [com.stuartsierra.component :as component]
            [om.next.server :as om]
            [yada.yada :as yada]))

(def transit #{"application/transit+msgpack"
               "application/transit+json;q=0.9"})

(defn om-next-query-resource [parser env]
  (yada/resource
   {:methods
    {:post {:consumes transit
            :produces transit
            :response (fn [ctx] (parser env (:body ctx)))}}}))

(defn coffee-table-app-routes [datomic-conn]
  ["" [;; Om Next-based internal API
       ["/internal-api" (om-next-query-resource (om/parser {:read readf
                                                            :mutate mutatef})
                                                {:conn datomic-conn})]
       ;; Nothing matched; terminate with 404,
       [true (yada/handler nil)]]])

(defrecord Routes [db]
  component/Lifecycle
  (start [component]
    (let [conn (:conn db)]
      (assoc component :routes (coffee-table-app-routes conn))))
  (stop [component]
    (dissoc component :routes)))

(defn new-app-routes []
  (map->Routes {}))
