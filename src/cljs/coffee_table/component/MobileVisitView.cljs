(ns coffee-table.component.MobileVisitView
  (:require-macros [devcards.core :as dc :refer [defcard defcard-om-next]])
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [formatters unparse]]
            [om-next-semantic.rating :refer [Rating]]))

(defui ^:once MobileVisitView
  Object
  (render [this]
          (let [props (om/props this)
                formatter (formatters :date)]
            (html [:div.text.segments
                   [:div (props :name)]
                   [:div (unparse formatter (props :date))]
                   [:div (get-in props [:address :city])]
                   [:div (:espresso-machine props)]
                   [:div (:grinder props)]
                   [:div (:roast props)]
                   [:div (:beverage-ordered props)]
                   ((om/factory Rating) {:rating (props :beverage-rating) :max-rating 5})
                   [:div (:beverage-notes props)]
                   ((om/factory Rating) {:rating (props :service-rating) :max-rating 5})
                   [:div (:service-notes props)]
                   ((om/factory Rating) {:rating (props :ambience-rating) :max-rating 5})
                   [:div (:ambience-notes props)]
                   [:div (:other-notes props)]]))))


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
