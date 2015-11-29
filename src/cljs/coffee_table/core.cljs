(ns ^:figwheel-always coffee-table.core
  (:require [om.core :as om]
            [coffee-table.api :refer [edn-xhr]]
            [coffee-table.state :refer [app-state]]
            [coffee-table.visits :refer [edn2json json2edn]]
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
      (om/root
       app app-state
       {:target
        (. js/document (getElementById "app"))
        :tx-listen
        (fn [tx-data root-cursor]
          (let [[tag & [value]] (:tag tx-data)]
            (case tag
              :create (edn-xhr
                       {:method :post
                        :url "/visits"
                        :data (json2edn value)
                        :on-complete
                        (fn [res]
                          (let [visit (assoc value :id (:id res))]
                            (om/transact! root-cursor :visits #(conj % visit))
                            (om/update! root-cursor :current-visit visit)
                            (om/update! root-cursor [:main-window :editing?] false)))})
              :update (edn-xhr
                       {:method :put
                        :url (str "/visits/" (:id value))
                        :data (json2edn value)
                        :on-complete
                        (fn [res]
                          (let [visits (:visits @root-cursor)
                                old-visit (some #(if (= (:id %) (:id value)) %) visits)]
                            (om/transact! root-cursor :visits
                                          #(replace {old-visit value} %))
                            (om/update! root-cursor [:main-window :editing?] false)))})
              :delete (edn-xhr
                       {:method :delete
                        :url (str "/visits/" value)
                        :on-complete
                        (fn [_]
                          (let [visits (:visits @root-cursor)
                                new-visits (vec (remove #(= (:id %) value) visits))]
                            (om/update! root-cursor :visits new-visits)
                            (om/update! root-cursor :current-visit (first new-visits))
                            (om/update! root-cursor [:main-window :editing?] false)))})
              nil)))}))}))

(set! (.-onload js/window) main!)
