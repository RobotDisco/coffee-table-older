(ns coffee-table.resource
  (:require [liberator.core :refer [defresource]]
            [liberator.representation :refer [ring-response]]
            [coffee-table.visits :as visits]))

;; a helper to create a absolute url for the entry with the given id
(defn build-entry-url [request id]
  (java.net.URL. (format "%s://%s:%s%s/%s"
                (name (:scheme request))
                (:server-name request)
                (:server-port request)
                (:uri request)
                (str id))))

(defresource visit-collection
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok (fn [ctx] (map #(str (build-entry-url (ctx :request) (:id %))) (visits/list-visits))))
