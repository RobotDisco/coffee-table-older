(ns coffee-table.component.MobileVisitView
  (:require-macros [devcards.core :as devcards :refer [defcard defcard-om-next]])
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [formatters unparse]]
            [om-next-semantic.fields :as fields]))

(defui ^:once MobileVisitView
  Object
  (render [this]
          (let [{:keys [editing buffer] :as props} (om/props this)
                ratingField (om/factory fields/RatingField)
                textField (om/factory fields/TextField)
                dateField (om/factory fields/DateField)
                textArea (om/factory fields/TextArea)
                formatter (formatters :date)]
            (html [:div.ui.form
                   (textField {:label "Cafe Name"
                               :value (:name buffer)
                               :readOnly (not editing)})
                   (dateField {:label "Date Visited"
                               :value (unparse formatter (:date buffer))
                               :readOnly (not editing)})
                   (textField {:label "City"
                               :value (get-in buffer [:address :city])
                               :readOnly (not editing)})
                   (textField {:label "Espresso Machine"
                               :value (:espresso-machine buffer)
                               :readOnly (not editing)})
                   (textField {:label "Grinder"
                               :value (:grinder buffer)
                               :readOnly (not editing)})
                   (textField {:label "Roast"
                               :value (:roast buffer)
                               :readOnly (not editing)})
                   (textField {:label "Beverage Ordered"
                               :value (:beverage-ordered buffer)
                               :readOnly (not editing)})
                   (ratingField {:label "Drink Rating"
                                 :rating (:beverage-rating buffer)
                                 :max-rating 5
                                 :interactive editing})
                   (textArea {:label "Tasting Notes"
                              :value (:beverage-notes buffer)
                              :readOnly (not editing)})
                   (ratingField {:label "Service Rating"
                                 :rating (:service-rating buffer)
                                 :max-rating 5
                                 :interactive editing})
                   (textArea {:label "Service Notes"
                              :value (:service-notes buffer)
                              :readOnly (not editing)})
                   (ratingField {:label "Ambience Rating"
                                 :rating (:ambience-rating buffer)
                                 :max-rating 5
                                 :interactive editing})
                   (textArea {:label "Ambience Notes"
                              :value (:ambience-notes buffer)
                              :readOnly (not editing)})
                   (textArea {:label "Other Notes"
                              :value (:other-notes buffer)
                              :readOnly (not editing)})
                   [:button.fluid.ui.button "See Address"]
                   [:button.fluid.ui.button {:visible false}]
                   [:button.fluid.ui.button "Edit"]
                   [:button.fluid.ui.negative.button "Delete"]]))))


(def visit-data
  {:editing false
   :buffer {:name "FKA Twigs Cafe"
            :date (time/now)
            :address {:address1 "117 Grimes Boulevard"
                      :address2 "CPL 593H Suite"
                      :city "Toronto"
                      :region "Ontario"
                      :country "Canada"}
            :espresso-machine "Elektra Micro Casa a Leva"
            :grinder "Mazzer Stepless Doserless"
            :roast "Intelligentsia Diablo"
            :beverage-ordered "Single shot espresso"
            :beverage-rating 4
            :beverage-notes "Something something something"
            :service-rating 3
            :service-notes "Something something something"
            :ambience-rating 5
            :ambience-notes "Something something something"
            :other-notes "Something something something"
            }})

(defcard mobile-visit-data
  visit-data)

(defcard-om-next mobile-visit
  MobileVisitView
  visit-data)
