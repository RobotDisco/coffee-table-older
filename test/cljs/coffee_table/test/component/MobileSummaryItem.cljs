(ns coffee-table.test.component.MobileSummaryItem
  (:require [cljs.test :refer-macros [deftest testing is are use-fixtures]]
            [cljs-react-test.utils :as tu]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [goog.dom :as gdom]
            [om.next :as om]
            [om.dom :as dom]
            [cljsjs.react]
            [cljsjs.react.dom]
            [cljs-time.core :refer [now]]
            [cljs-time.format :refer [unparse formatters]]
            [coffee-table.test.utils :as tutils :refer [c]]
            [coffee-table.component.MobileSummaryItem :as summary]
            [om-next-semantic.rating :refer [Rating]]))

(use-fixtures :each tutils/test-container)

(deftest MobileSummaryItem
  (testing "displays name, stars, date"
    (let [props {:name "FKA Twigs Cafe"
                 :date (now)
                 :beverage-rating 3}
          summary (om/factory summary/MobileSummaryItem)
          res (js/ReactDOM.render (summary props) c)
          [name-node date-node] (sel c [:span])
          rating (tu/find-one-by-type res Rating)]
      (is (= (props :name) (.-innerHTML name-node)))
      (is (= ((om/props rating) :rating) (props :beverage-rating)))
      (is (= (unparse (formatters :date) (props :date))
             (.-innerHTML date-node))))))
