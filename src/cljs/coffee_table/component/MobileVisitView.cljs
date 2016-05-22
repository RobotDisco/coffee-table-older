(ns coffee-table.component.MobileVisitView
  (:require-macros [devcards.core :as devcards :refer [defcard defcard-om-next]])
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.coerce :refer [from-date]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [formatters unparse parse]]
            [om-next-semantic.fields :as fields]
            [coffee-table.visit :as visit]))

(defn changeFieldHandler [this key]
  (fn [e]
    (om/transact! this `[(~'edit-field {:key ~key :value ~(.. e -target -value)})])))

(defn changeDateFieldHandler [this key]
  (fn [e]
    (om/transact! this `[(~'edit-date-field {:key ~key :value ~(.. e -target -value)})])))

(defn changeRatingFieldHandler [this key]
  (fn [value]
    (om/transact! this `[(~'edit-field {:key ~key :value ~value})])))

(defn buttons [this buffer editing]
  (let [{:keys [:db/id]} buffer
        save-action (if (visit/pending? buffer) 'visit/add 'buffer/save)
        cancel-action (if (visit/pending? buffer) 'app/list-mode 'buffer/revert)]
    (html (if editing
            [:div
             [:button.fluid.ui.button.positive
              {:onClick #(om/transact! this `[(~save-action) :app/visits :app/editing :app/mode])}
              "Save"]
             [:button.fluid.ui.button
              {:onClick #(om/transact! this `[(~cancel-action) :app/buffer :app/editing])}
              "Cancel"]
             (when-not (visit/pending? buffer)
               [:button.fluid.ui.button {:visible false :disabled true}]
               [:button.fluid.ui.negative.button
                {:onClick #(om/transact! this `[(~'visit/delete) :app/visits :app/mode])}
                "Delete"])]
            [:div [:button.fluid.ui.button "See Address"]
             [:button.fluid.ui.button {:visible false :disabled true}]
             [:button.fluid.ui.button
              {:onClick #(om/transact! this `[(~'buffer/edit) :app/editing])}
              "Edit"]]))))

(defui ^:once MobileVisitView
  static om/IQuery
  (query [this] [:app/editing :app/buffer])
  Object
  (render [this]
          (let [{:keys [app/buffer app/editing] :as props} (om/props this)
                ratingField (om/factory fields/RatingField)
                textField (om/factory fields/TextField)
                dateField (om/factory fields/DateField)
                textArea (om/factory fields/TextArea)
                formatter (formatters :date)]
            (html [:div.ui.form
                   (textField {:label "Cafe Name"
                               :react-key "buffer-name"
                               :value (:visit/name buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :visit/name)})
                   (dateField {:label "Date Visited"
                               :react-key "buffer-date"
                               :value (unparse formatter (from-date (:visit/date buffer)))
                               :readOnly (not editing)
                               :onChange (changeDateFieldHandler this :visit/date)})
                   (textField {:label "City"
                               :react-key "buffer-city"
                               :value (get-in buffer [:visit/address :address/city])
                               :readOnly (not editing)})
                   (textField {:label "Espresso Machine"
                               :react-key "buffer-machine"
                               :value (:visit/espresso-machine buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :visit/espresso-machine)})
                   (textField {:label "Grinder"
                               :react-key "buffer-grinder"
                               :value (:visit/grinder buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :visit/grinder)})
                   (textField {:label "Roast"
                               :react-key "buffer-roaster"
                               :value (:visit/roast buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :visit/roast)})
                   (textField {:label "Beverage Ordered"
                               :react-key "beverage-ordered"
                               :value (:visit/beverage-ordered buffer)
                               :readOnly (not editing)
                               :onChange (changeFieldHandler this :visit/beverage-ordered)})
                   (ratingField {:label "Drink Rating"
                                 :react-key "buffer-beverage-rating"
                                 :rating (:visit/beverage-rating buffer)
                                 :max-rating 5
                                 :interactive editing
                                 :onRate (changeRatingFieldHandler this :visit/beverage-rating)})
                   (textArea {:label "Tasting Notes"
                              :react-key "buffer-beverage-notes"
                              :value (:visit/beverage-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :visit/beverage-notes)})
                   (ratingField {:label "Service Rating"
                                 :react-key "buffer-service-rating"
                                 :rating (:visit/service-rating buffer)
                                 :max-rating 5
                                 :interactive editing
                                 :onRate (changeRatingFieldHandler this :visit/service-rating)})
                   (textArea {:label "Service Notes"
                              :react-key "buffer-service-notes"
                              :value (:visit/service-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :visit/service-notes)})
                   (ratingField {:label "Ambience Rating"
                                 :react-key "buffer-ambience-rating"
                                 :rating (:visit/ambience-rating buffer)
                                 :max-rating 5
                                 :interactive editing
                                 :onRate (changeRatingFieldHandler this :visit/ambience-rating)})
                   (textArea {:label "Ambience Notes"
                              :react-key "buffer-ambience-notes"
                              :value (:visit/ambience-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :visit/ambience-notes)})
                   (textArea {:label "Other Notes"
                              :react-key "buffer-other-notes"
                              :value (:visit/other-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :visit/other-notes)})
                   (buttons this buffer editing)]))))

(def visit-view (om/factory MobileVisitView))
