(ns coffee-table.test.utils
  (:require [cljs-react-test.utils :as tu]
            [cljsjs.react]
            [cljsjs.react.dom]))

(def ^:dynamic c)

(defn test-container [test-fn]
  (binding [c (tu/new-container!)]
    (test-fn)
    (.unmountComponentAtNode js/ReactDOM c)))
