(ns coffee-table.components.app
  (:require [om.core :as om]
            [om.dom :as dom]
            [om-semantic.rating :as r]
            [sablono.core :as html :refer-macros [html]]
            [coffee-table.components.main-area :refer [visit-detail]]
            [coffee-table.components.sidebar :refer [visit-list]]))


(defn app [app owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "CoffeeTable")
    om/IRender
    (render [this]
      (html [:div.ui.grid
             [:div.four.wide.column
              (om/build visit-list app)]
             [:div.twelve.wide.column
              [:div.ui.basic.segment
               (om/build visit-detail (:current-visit app))]]]))))
