(ns coffee-table.core
  (:require [om.core :as om]
            [om.dom :as dom]))

(defn widgetitem [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil app))))

(defn widget [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/ul nil
              (om/build-all widgetitem app)))))

(om/root widget [1 2 3 4 5 6 7 8 9 10]
         {:target (. js/document (getElementById "app"))})
