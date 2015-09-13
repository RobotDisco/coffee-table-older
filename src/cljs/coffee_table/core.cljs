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
                                 (om/build r/rating
                                           {:rating (:beverage_rating visit)})))))))

(defn visit-detail [visit owner]
  (reify
    om/IRender
    (render [this]
      (dom/form #js {:className "ui form"}
                (dom/div #js {:className "field"}
                         (dom/label nil "Cafe Name")
                         (dom/input #js {:type "text"
                                         :name "cafe-name"
                                         :value (:cafe_name visit)}))
                (dom/div #js {:className "field"}
                         (dom/label nil "Date Visited")
                         (dom/input #js {:type "date"
                                         :name "date-visited"
                                         :value (:dated_visited visit)}))
                (dom/div #js {:className "field"}
                         (dom/label nil "Beverage Ordered")
                         (dom/input #js {:type "text"
                                         :name "beverage"
                                         :value (:beverage visit)}))
                (dom/div #js {:className "field"}
                         (dom/label nil "Beverage Rating")
                         (om/build r/rating
                                   {:rating (:beverage_rating visit)}))))))

(defn coffee-table [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "ui grid"}
               (dom/div #js {:className "four wide column"}
                        (apply dom/div #js {:className "ui divided items"}
                               (om/build-all visit-summary (:visits app))))
               (dom/div #js {:className "twelve wide column"}
                        (dom/div #js {:className "ui basic segment"}
                                 (om/build visit-detail (first (:visits app)))))))))

(GET "http://192.168.99.100:3449/visits" {:handler handler :keywords? true
                                          :response-format :json})
(om/root coffee-table app
         {:target (. js/document (getElementById "app"))})
