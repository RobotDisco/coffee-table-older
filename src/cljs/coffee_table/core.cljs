(ns coffee-table.core
  (:require [coffee-table.components.app :as app]
            [coffee-table.mock-data :as mock-data]
            [coffee-table.controllers.controls :as controls-con]
            [om.core :as om]
            [cljs.core.async :refer [chan]])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]))

(enable-console-print!)

(defonce opts
  {:channels {:controls (chan)}})

(defonce app-state
  (atom mock-data/initial-state))

(defn main [target state]
  (let [channels (:channels opts)]
    (om/root app/app app-state
             {:target target
              :opts opts})
    (go (while true
          (alt!
            (:controls channels) ([v]
                                  (swap! state
                                         (partial controls-con/control-event
                                                  target
                                                  (first v)
                                                  (second v)))))))))

(defn setup! []
  (main (. js/document (getElementById "app")) app-state))

(set! (.-onload js/window) setup!)
