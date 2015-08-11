(ns coffee-table.core
  (:require [compojure.core :refer [ANY GET defroutes]]
            [compojure.route :as route]
            [liberator.dev :refer [wrap-trace]]
            [coffee-table.resource :as resource]))

(defroutes app
  (route/resources "/")
  (ANY "/visits" [] resource/visit-collection)
  (ANY "/visits/:id{[0-9]+}" [id] (resource/visit (Integer/parseInt id)))
  (route/not-found "<h1>Page not found</h1>"))
