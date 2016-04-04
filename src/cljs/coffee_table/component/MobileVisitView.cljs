(ns coffee-table.component.MobileVisitView
  (:require-macros [devcards.core :as devcards :refer [defcard defcard-om-next]])
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [formatters unparse parse]]
            [om-next-semantic.fields :as fields]))

(defn mutate [{:keys [state] :as env} key params]
  (case key
    toggle-edit {:keys [:editing]
                 :action (let [newval (:value params)]
                           (swap! state assoc :editing newval))}
    edit-field {:keys [:buffer]
                :action (let [{:keys [value key]} params
                              buffer (:buffer @state)]
                          (swap! state assoc-in [:buffer key] value))}
    edit-date-field {:keys [:buffer]
                     :action (let [{:keys [value key]} params
                                   buffer (:buffer @state)]
                               (swap! state assoc-in [:buffer key] (parse (formatters :date) value)))}))

(defn read [{:keys [state] :as env} key params]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-find})))

(def parser (om/parser {:read read :mutate mutate}))

(defonce app-state
  (atom {:editing false
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
                  }}))

(defonce reconciler
  (om/reconciler {:state app-state
                  :parser parser}))

(defn changeFieldHandler [this key]
  (fn [e]
    (om/transact! this `[(~'edit-field {:key ~key :value ~(.. e -target -value)})])))

(defn changeDateFieldHandler [this key]
  (fn [e]
    (om/transact! this `[(~'edit-date-field {:key ~key :value ~(.. e -target -value)})])))


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
                                 :interactive editing})
                   (textArea {:label "Tasting Notes"
                              :value (:beverage-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :beverage-notes)})
                   (ratingField {:label "Service Rating"
                                 :rating (:service-rating buffer)
                                 :max-rating 5
                                 :interactive editing})
                   (textArea {:label "Service Notes"
                              :value (:service-notes buffer)
                              :readOnly (not editing)
                              :onChange (changeFieldHandler this :service-notes)})
                   (ratingField {:label "Ambience Rating"
                                 :rating (:ambience-rating buffer)
                                 :max-rating 5
                                 :interactive editing})
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


(defcard mobile-visit-data
  app-state)

(defcard-om-next mobile-visit
  MobileVisitView
  reconciler)
