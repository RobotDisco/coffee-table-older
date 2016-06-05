(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [ajax.core :refer [POST transit-request-format]]
            [goog.date]
            [cognitect.transit :as transit]
            [coffee-table.state :as state]
            [coffee-table.parser :as parser]
            [coffee-table.component.App :refer [App]])
  (:import goog.date.UtcDateTime))


(enable-console-print!)

(def transit-writers
  {:handlers
   {UtcDateTime (transit/write-handler
                 (constantly "m")
                 (fn [v] (.getTime v))
                 (fn [v] (str (.getTime v))))}})

(defn handler [response cb]
  (cb response))

(defn send [query cb]
  (POST "/query" {:params (:remote query)
                  :format (transit-request-format {:writer (transit/writer :json transit-writers)})
                  :handler #(cb %)}))

(defonce reconciler (om/reconciler {:state state/app-state
                                    :parser parser/parser
                                    :send send}))

(om/add-root! reconciler App (gdom/getElement "app"))
