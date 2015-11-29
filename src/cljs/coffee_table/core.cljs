(ns ^:figwheel-always coffee-table.core
  (:require [om.core :as om]
            [coffee-table.api :refer [edn-xhr]]
            [coffee-table.state :refer [app-state]]
            [coffee-table.visits :refer [edn2json]]
            [coffee-table.components.app :refer [app]]))

(enable-console-print!)

(defn main! []
  (edn-xhr
   {:method :get
    :url "/visits"
    :on-complete
    (fn [res]
      (swap! app-state assoc :visits (mapv edn2json res))
      (swap! app-state assoc :current-visit (first (:visits @app-state)))
      (om/root app app-state {:target (. js/document (getElementById "app"))}))}))

(set! (.-onload js/window) main!)
