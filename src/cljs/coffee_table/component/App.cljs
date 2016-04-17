(ns coffee-table.component.App
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [coffee-table.component.MobileSummaryList :as list]
            [coffee-table.component.MobileSummaryItem :as item]
            [coffee-table.component.MobileVisitView :as view]))

(defui ^:once App
  static om/IQuery
  (query [this] `[{:app/visits ~(om/get-query item/MobileSummaryItem)}
                  :app/mode
                  :app/editing
                  :app/buffer])
  Object
  (render [this]
          (let [{:keys [app/mode app/visits
                        app/editing app/buffer]} (om/props this)]
            (html
             [:div#coffee-table-app
              (if (= mode :list)
                [:div
                 (list/summary-list visits)
                 [:button.ui.fluid.button
                  {:onClick #(om/transact! this `[(~'visit/new) :app/buffer :app/editing :app/mode])}
                  "Add Visit"]]
                (view/visit-view {:app/buffer buffer
                                  :app/editing editing}))]))))
