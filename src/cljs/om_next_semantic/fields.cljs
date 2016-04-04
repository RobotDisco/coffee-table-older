(ns om-next-semantic.fields
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [om-next-semantic.rating :refer [Rating]]))

(defui ^:once TextField
  Object
  (render [this]
          (let [{:keys [label] :as props} (om/props this)]
            (dom/div #js {:className "field"}
                     (dom/label nil label)
                     (dom/input (clj->js (merge props
                                                {:type "text"})))))))

(defui ^:once DateField
  Object
  (render [this]
          (let [{:keys [label] :as props} (om/props this)]
            (dom/div #js {:className "field"}
                     (dom/label nil label)
                     (dom/input (clj->js (merge props
                                                {:type "date"})))))))

(defui ^:once TextArea
  Object
  (render [this]
          (let [{:keys [label] :as props} (om/props this)]
            (dom/div #js {:className "field"}
                     (dom/label nil label)
                     (dom/textarea (clj->js props))))))

(defui ^:once RatingField
  Object
  (render [this]
          (let [{:keys [label rating max-rating interactive] :as props} (om/props this)
                widget (om/factory Rating)]
            (dom/div #js {:className "field"}
                     (dom/label nil label)
                     (widget {:rating rating
                              :max-rating max-rating
                              :interactive interactive})))))
