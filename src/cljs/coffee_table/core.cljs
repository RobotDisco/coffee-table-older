(ns coffee-table.core
  (:require [om.core :as om]
            [om.dom :as dom]
            [om-semantic.rating :as r]
            [ajax.core :refer [GET]]))

(defonce app (atom {:visits []}))

(defn handler [response]
  (swap! app assoc :visits response))

(defn visit-summary [visit owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "item"}
               (dom/div #js {:className "content"}
                        (dom/div #js {:className "header"} (:cafe_name visit))
                        (dom/div #js {:className "meta"}
                                 (dom/span nil (clojure.string/join
                                                (list "Visited: "
                                                      (:date_visited visit)))))
                        (dom/div #js {:className "description"}
                                 (dom/span nil "Beverage Rating: ")
                                 (om/build r/rating {})))))))

(defn coffee-table [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "ui divided items"}
                 (om/build-all visit-summary (:visits @app))))))

(GET "http://192.168.99.100:3449/visits" {:handler handler :keywords? true
                                          :response-format :json})
(om/root coffee-table app
         {:target (. js/document (getElementById "app"))})
