(ns coffee-table.component.MobileVisitView
  (:require-macros [devcards.core :as dc :refer [defcard defcard-om-next]])
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [formatters unparse]]
            [om-next-semantic.fields :as fields]))

(defui ^:once MobileVisitView
  Object
  (render [this]
          (let [props (om/props this)
                ratingField (om/factory fields/RatingField)
                textField (om/factory fields/TextField)
                dateField (om/factory fields/DateField)
                textArea (om/factory fields/TextArea)
                formatter (formatters :date)]
            (html [:div.ui.form
                   (textField {:label "Cafe Name"
                               :value (:name props)
                               :readOnly true})
                   (dateField {:label "Date Visited"
                               :value (unparse formatter (:date props))
                               :readOnly true})
                   (textField {:label "City"
                               :value (get-in props [:address :city])
                               :readOnly true})
                   (textField {:label "Espresso Machine"
                               :value (:espresso-machine props)
                               :readOnly true})
                   (textField {:label "Grinder"
                               :value (:grinder props)
                               :readOnly true})
                   (textField {:label "Roast"
                               :value (:roast props)
                               :readOnly true})
                   (textField {:label "Beverage Ordered"
                               :value (:beverage-ordered props)})
                   (ratingField {:label "Drink Rating"
                                 :rating (props :beverage-rating)
                                 :max-rating 5})
                   (textArea {:label "Tasting Notes"
                              :value (:beverage-notes props)
                              :readOnly true})
                   (ratingField {:label "Service Rating"
                                 :rating (props :service-rating)
                                 :max-rating 5})
                   (textArea {:label "Service Notes"
                              :value (:service-notes props)
                              :readOnly true})
                   (ratingField {:label "Ambience Rating"
                                 :rating (props :ambience-rating)
                                 :max-rating 5})
                   (textArea {:label "Ambience Notes"
                              :value (:ambience-notes props)
                              :readOnly true})
                   (textArea {:label "Other Notes"
                              :value (:other-notes props)
                              :readOnly true})
                   [:button.fluid.ui.button "See Address"]
                   [:button.fluid.ui.button {:visible false}]
                   [:button.fluid.ui.button "Edit"]
                   [:button.fluid.ui.negative.button "Delete"]]))))


(defonce visit-data
  {:name "FKA Twigs Cafe"
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
   })

(defcard mobile-visit-data
  visit-data)

(defcard-om-next mobile-visit
  MobileVisitView
  visit-data)
