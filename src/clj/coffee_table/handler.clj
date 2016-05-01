(ns coffee-table.handler
  (:require [bidi.ring :refer [make-handler]]
            [ring.util.response :as res]))

(defn index-handler
  [request]
  (res/response "Homepage"))

(def handler
  (make-handler ["/" {"index.html" index-handler}]))
