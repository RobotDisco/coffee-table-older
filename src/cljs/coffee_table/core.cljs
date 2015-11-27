(ns ^:figwheel-always coffee-table.core
  (:require [om.core :as om]
            [goog.events :as events]
            [cljs.reader :as reader]
            [coffee-table.state :refer [app-state]]
            [coffee-table.components.app :refer [app]])
  #_ (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

#_ (defn on-edit [id title]
  (edn-xhr {:method :put
            :url (str "visits/" id "/update")
            :data {}
            :on-complete (fn [res]
                           (println "server response:" res))}))

(defn main [target state]
  (om/root app state {:target target}))

(defn setup! []
  (main (. js/document (getElementById "app")) app-state))

(set! (.-onload js/window) setup!)
