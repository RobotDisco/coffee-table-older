(ns coffee-table.resource
  (:require [liberator.core :refer [defresource]]
            [liberator.representation :refer [ring-response]]
            [ring.util.response :as r]))

(defresource visit
  :allowed-methods [:post :get]
  :available-media-types ["application/json"]
  :handle-ok (fn [ctx] [])
  :handle-created (fn [ctx] (ring-response
                             (r/created "http://localhost/visits/1"))))
