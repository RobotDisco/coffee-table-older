(ns coffee-table.resource
  (:require [liberator.core :refer [defresource]]))

(defresource visit
  :available-media-types ["application/json"]
  :handle-ok (fn [ctx] []))
