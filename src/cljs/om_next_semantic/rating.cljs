(ns om-next-semantic.rating
  (:require [cljsjs.semantic-ui]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom])
  (:require-macros [devcards.core :as devcards :refer [defcard]]))

(defui ^:once Rating
  Object
  (componentDidMount [this]
                     (.rating (js/$ (dom/node this)) #js {:interactive false}))
  (componentDidUpdate [this _ _]
                      (let [props (om/props this)
                            rating (props :rating)
                            node (-> this
                                     dom/node
                                     js/$)]
                        (.rating node "set rating" rating)))
  (render [this]
          (let [props (om/props this)
                rating (props :rating)
                max-rating (props :max-rating)]
            (dom/div #js {:className "ui rating"
                          :data-rating rating
                          :data-max-rating max-rating
                          :interactive false}))))

(defcard rating
  ((om/factory Rating) {:rating 4 :max-rating 5}))
