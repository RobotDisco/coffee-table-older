(ns coffee-table.components.app
  (:require [om.core :as om]
            [om.dom :as dom]
            [om-semantic.rating :as r]
            [cljs.core.async :refer [put!]]
            [sablono.core :as html :refer-macros [html]]
            [coffee-table.components.main-area :refer [visit-detail]]
            [coffee-table.components.sidebar :refer [visit-list]]))


(defn app [app owner opts]
  (reify
    om/IDisplayName
    (display-name [_]
      "CoffeeTable")
    om/IRender
    (render [this]
      (html [:div.ui.grid
             [:div.four.wide.column
              (om/build visit-list
                        (select-keys app [:visits :current-visit])
                        {:opts opts})]
             [:div.twelve.wide.column
              [:div.ui.basic.segment
               (om/build visit-detail
                         (:current-visit app)
                         {:opts opts})]]]))))
