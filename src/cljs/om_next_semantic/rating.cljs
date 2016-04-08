(ns om-next-semantic.rating
  (:require [cljsjs.semantic-ui]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom])
  (:require-macros [devcards.core :as devcards :refer [defcard]]))

(defui ^:once Rating
  Object
  (componentDidMount [this]
                     (let [{:keys [rating max-rating interactive onRate] :or {interaxtive false onRate #()}} (om/props this)]
                       (.rating (-> this dom/node js/$)
                                #js {:interactive interactive
                                     :initialRating rating
                                     :maxRating max-rating
                                     :onRate onRate})))
  (componentDidUpdate [this prevprops _]
                      (let [{:keys [interactive rating] :as props} (om/props this)
                            node (-> this dom/node js/$)]
                        (when-not (== rating (:rating prevprops)
                                      (.rating node "set rating" rating)))
                        (when-not (== interactive (:interactive prevprops))
                          (if interactive
                            (.rating node "enable")
                            (.rating node "disable")))))
  (render [this]
          (dom/div #js {:className "ui rating"})))

(defcard rating
  ((om/factory Rating) {:rating 4 :max-rating 5}))
