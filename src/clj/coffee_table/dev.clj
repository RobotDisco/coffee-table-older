(ns coffee-table.dev
  (:require [coffee-table.core :refer [app]]
            [liberator.dev :refer [wrap-trace]]
            [ring.server.standalone :refer [serve]]
            [com.unbounce.encors :refer [wrap-cors]]))

(def app-debug
  (-> app
      (wrap-trace :header :ui)
      (wrap-cors {:allowed-origins :star-origin
                  :allowed-methods #{:head :options :get :post :put :delete
                                     :patch :trace}
                  :request-headers #{}
                  :exposed-headers nil
                  :max-age nil
                  :allow-credentials? false
                  :origin-varies? false
                  :require-origin? false
                  :ignore-failures? true})))
