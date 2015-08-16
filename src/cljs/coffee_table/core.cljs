(ns coffee-table.core
  (:require [om.core :as om]
            [om.dom :as dom]
            [ajax.core :refer [GET]]))

(defonce app (atom {:visits []}))

(defn handler [response]
  (swap! app assoc :visits response))

(defn visit-summary [visit owner]
  (reify
    om/IRender
    (render [this]
      (dom/tr nil
              (dom/td nil (:id visit))
              (dom/td nil (:cafe_name visit))
              (dom/td nil (:date_visited visit))
              (dom/td nil (:beverage visit))
              (dom/td nil (:beverage_rating visit))))))

(defn coffee-table [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/table #js {:style #js {:border "1px solid black"
                                  :borderCollapse "collapse"}}
                 (dom/thead nil
                            (dom/tr nil
                                    (dom/th nil "ID")
                                    (dom/th nil "Cafe Name")
                                    (dom/th nil "Date Visited")
                                    (dom/th nil "Beverage Ordered")
                                    (dom/th nil "Beverage Rating")))
                 (dom/tbody nil
                             (om/build-all visit-summary (:visits @app)))))))

(GET "http://192.168.99.100:3000/visits" {:handler handler :keywords? true
                                          :response-format :json})
(om/root coffee-table app
         {:target (. js/document (getElementById "app"))})
