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
            [coffee-table.component.MobileSummaryItem :as summary]))

(def ^:dynamic c)

(use-fixtures :each (fn [test-fn]
                      (binding [c (tu/new-container!)]
                        (test-fn)
                        (.unmountComponentAtNode js/ReactDOM c))))

(deftest MobileSummaryItem
  (testing "displays name, stars, date"
    (let [props {:name "FKA Twigs Cafe"
                 :date (now)
                 :beverage-rating 3}
          summary (om/factory summary/MobileSummaryItem)
          _ (js/ReactDOM.render (summary props) c)
          [name-node beverage-node date-node] (sel c [:p])]
      (is (= (props :name) (.-innerHTML name-node)))
      (is (= (str (props :beverage-rating)) (.-innerHTML beverage-node)))
      (is (= (unparse (formatters :date) (props :date))
             (.-innerHTML date-node))))))
