(ns coffee-table.repl
  (:require [coffee-table.core :refer [app]]
            [liberator.dev :refer [wrap-trace]]
            [ring.server.standalone :refer [serve]]))

(def app-debug
  (-> app
      (wrap-trace :header :ui)))

(defn repl-start []
  (serve app-debug))
