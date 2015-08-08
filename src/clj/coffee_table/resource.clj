(ns coffee-table.resource
  (:require [liberator.core :refer [defresource]]
            [liberator.representation :refer [ring-response]]
            [coffee-table.visits :as visits]
            [clojure.java.io :as io]
            [clojure.data.json :as json]))

;; convert the body to a reader. Useful for testing in the repl
;; where setting the body to a string is much simpler.
(defn body-as-string [ctx]
  (if-let [body (get-in ctx [:request :body])]
    (condp instance? body
      java.lang.String body
      (slurp (io/reader body)))))

;; For PUT and POST parse the body as json and store in the context
;; under the given key.
(defn parse-json [ctx key value-reader]
  (when (#{:put :post} (get-in ctx [:request :request-method]))
    (try
      (if-let [body (body-as-string ctx)]
        (let [data (json/read-str body :key-fn keyword :value-fn value-reader)]
          [false {key data}])
        {:message "No body"})
      (catch Exception e
        (.printStackTrace e)
        {:message (format "IOException: %s" (.getMessage e))}))))

;; a helper to create a absolute url for the entry with the given id
(defn build-entry-url [request id]
  (java.net.URL. (format "%s://%s:%s%s/%s"
                (name (:scheme request))
                (:server-name request)
                (:server-port request)
                (:uri request)
                (str id))))

;; For PUT and POST check if the content type is json.
(defn check-content-type [ctx content-types]
  (if (#{:put :post} (get-in ctx [:request :request-method]))
    (or
     (some #{(get-in ctx [:request :headers "content-type"])}
           content-types)
     [false {:message "Unsupported Content-Type"}])
    true))

(defresource visit-collection
  :allowed-methods [:get :post]
  :available-media-types ["application/json"]
  :known-content-type? #(check-content-type % ["application/json"])
  :malformed? #(parse-json % ::data visits/json-value-reader)
  :post! #(let [id (:id (visits/create-visit<! (::data %)))]
            {::id id})
  :post-redirect? true
  :location #(build-entry-url (get % :request) (get % ::id))
  :handle-ok (fn [ctx] (map #(str (build-entry-url (ctx :request) (:id %))) (visits/list-visits))))
