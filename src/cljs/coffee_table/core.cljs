(ns coffee-table.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [coffee-table.state :as state]
            [coffee-table.parser :as parser]
            [coffee-table.component.App :refer [App]]
            [ajax.core :refer [POST]]))

(enable-console-print!)

(defn handler [response cb]
  (cb response))

(defn send [query cb]
  (POST "/query" {:params (:remote query)
                  :handler #(handler % cb)}))

(defonce reconciler (om/reconciler {:state state/app-state
                                    :parser parser/parser
                                    :send send}))

(om/add-root! reconciler App (gdom/getElement "app"))
