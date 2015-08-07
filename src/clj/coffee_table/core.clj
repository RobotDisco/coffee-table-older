(ns coffee-table.core
  (:require [compojure.core :refer [ANY GET defroutes]]
            [compojure.route :as route]
            [liberator.dev :refer [wrap-trace]]
            [coffee-table.resource :as resource]))

(defroutes app
  (GET "/" [] "<h1>Hello World</h1>")
  (ANY "/visits" [] resource/visit-collection)
  (route/not-found "<h1>Page not found</h1>"))
