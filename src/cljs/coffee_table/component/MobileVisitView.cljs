(ns coffee-table.component.MobileVisitView
  (:require-macros [devcards.core :as devcards :refer [defcard defcard-om-next]])
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [formatters unparse parse]]
            [om-next-semantic.fields :as fields]))

(defn changeFieldHandler [this key]
  (fn [e]
    (om/transact! this `[(~'edit-field {:key ~key :value ~(.. e -target -value)})])))

(defn changeDateFieldHandler [this key]
  (fn [e]
    (om/transact! this `[(~'edit-date-field {:key ~key :value ~(.. e -target -value)})])))

(defn changeRatingFieldHandler [this key]
  (fn [value]
    (om/transact! this `[(~'edit-field {:key ~key :value ~value})])))


(defui ^:once MobileVisitView
  static om/IQuery
  (query [this] [:editing :buffer])
  Object
  (render [this]
          (let [{:keys [buffer editing] :as props} (om/props this)
                ratingField (om/factory fields/RatingField)
                textField (om/factory fields/TextField)
                dateField (om/factory fields/DateField)
                textArea (om/factory fields/TextArea)
                formatter (formatters :date)]
            (html [:div.ui.form
                   (textField {:label "Cafe Name"
                               :value (:name buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :name)})
                   (dateField {:label "Date Visited"
                               :value (unparse formatter (:date buffer))
                               :readOnly (not editing)
                               :onChange (changeDateFieldHandler this :date)})
                   (textField {:label "City"
                               :value (get-in buffer [:address :city])
                               :readOnly (not editing)})
                   (textField {:label "Espresso Machine"
                               :value (:espresso-machine buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :espresso-machine)})
                   (textField {:label "Grinder"
                               :value (:grinder buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :grinder)})
                   (textField {:label "Roast"
                               :value (:roast buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :roast)})
                   (textField {:label "Beverage Ordered"
                               :value (:beverage-ordered buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :beverage-ordered)})
                   (ratingField {:label "Drink Rating"
                                 :rating (:beverage-rating buffer)
                                 :max-rating 5
                                 :interactive editing
                                 :onRate (changeRatingFieldHandler this :beverage-rating)})
                   (textArea {:label "Tasting Notes"
                              :value (:beverage-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :beverage-notes)})
                   (ratingField {:label "Service Rating"
                                 :rating (:service-rating buffer)
                                 :max-rating 5
                                 :interactive editing
                                 :onRate (changeRatingFieldHandler this :service-rating)})
                   (textArea {:label "Service Notes"
                              :value (:service-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :service-notes)})
                   (ratingField {:label "Ambience Rating"
                                 :rating (:ambience-rating buffer)
                                 :max-rating 5
                                 :interactive editing
                                 :onRate (changeRatingFieldHandler this :ambience-rating)})
                   (textArea {:label "Ambience Notes"
                              :value (:ambience-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :ambience-notes)})
                   (textArea {:label "Other Notes"
                              :value (:other-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :other-notes)})
                   [:button.fluid.ui.button "See Address"]
                   [:button.fluid.ui.button {:visible false}]
                   [:button.fluid.ui.button
                    {:onClick #(om/transact! this `[(~'toggle-edit {:value ~(not editing)})])}
                    (if editing "Edit Y" "Edit N")]
                   [:button.fluid.ui.negative.button "Delete"]]))))
