(ns coffee-table.resource
  (:require [liberator.core :refer [defresource]]
            [liberator.representation :refer [ring-response]]
            [coffee-table.visits :as visits]))

(defresource visit-collection
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok (fn [ctx] (visits/list-visits)))
