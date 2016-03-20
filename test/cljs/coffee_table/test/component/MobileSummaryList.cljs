(ns coffee-table.test.component.MobileSummaryList
  (:require [cljs.test :refer-macros [deftest testing is are use-fixtures]]
            [cljs-react-test.utils :as tu]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [goog.dom :as gdom]
            [om.next :as om]
            [om.dom :as dom]
            [cljsjs.react]
            [cljsjs.react.dom]
            [cljs-time.core :refer [now]]
            [coffee-table.component.MobileSummaryList :as list]
            [coffee-table.component.MobileSummaryItem :as summary]))

(def ^:dynamic c)

(use-fixtures :each (fn [test-fn]
                      (binding [c (tu/new-container!)]
                        (test-fn)
                        (.unmountComponentAtNode js/ReactDOM c))))

(deftest MobileSummaryList
  (testing "displays all the provided visits in order"
    (let [props {:visits [{:name "FKA Twigs Cafe"
                           :date (now)
                           :beverage-rating 3
                           }
                          {:name "Grimes Bistro"
                           :date (now)
                           :beverage-rating 4}
                          {:name "Tango Palace"
                           :date (now)
                           :beverage-rating 5}]}
          list (om/factory list/MobileSummaryList)
          res (js/ReactDOM.render (list props) c)
          [twigs grimes tango :as items] (tu/find-by-type res summary/MobileSummaryItem)]
      (is (= (count items) (count (:visits props))))
      (is (= (:name (om/props twigs)) (get-in props [:visits 0 :name])))
      (is (= (:name (om/props grimes)) (get-in props [:visits 1 :name])))
      (is (= (:name (om/props tango)) (get-in props [:visits 2 :name]))))))
