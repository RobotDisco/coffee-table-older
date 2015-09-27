(ns om-semantic.rating
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [jayq.core :refer [$]]))

(enable-console-print!)

;; Rating component

(defn rating
  "A simple rating component for Om using Semantic UI CSS"
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "Rating")
    om/IDidMount
    (did-mount [_]
      (let [rating (:rating data)
            max-rating 5]
        (.rating ($ (om/get-node owner)) #js {"initialRating" rating
                                              "maxRating" max-rating
                                              "interactive" false})))
    om/IDidUpdate
    (did-update [_ _ _]
      (let [rating (:rating data)]
        (-> owner
            om/get-node
            $
            (.rating "set rating", rating))))
    om/IRender
    (render [_]
      (dom/div #js {:className "ui rating"}))))
