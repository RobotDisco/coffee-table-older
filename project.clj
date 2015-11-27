(defproject net.robot-disco/coffee-table "0.0.1-SNAPSHOT"
  :description "A Coffeeshop logger/review webapp"
  :url "http://github.com/RobotDisco/coffee-table"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.2.374"]
                 [liberator "0.13"]
                 [compojure "1.4.0"]
                 [com.datomic/datomic-free "0.9.5130" :exclusions [joda-time]]
                 [org.clojure/clojurescript "1.7.122"]
                 [org.omcljs/om "0.9.0"]
                 [sablono "0.4.0"]
                 [om-semantic "0.1.5-SNAPSHOT"]
                 [com.andrewmcveigh/cljs-time "0.3.14"]
                 [javax.servlet/servlet-api "2.5"]]
  :source-paths ["src/clj"]
  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.4.1"]
            [lein-ring "0.9.7"]]
  :ring {:handler coffee-table.core/handler}
  :figwheel {:ring-handler coffee-table.core/handler}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel true
                        :compiler {:main coffee-table.core
                                   :output-to "resources/public/js/out/app.js"
                                   :output-dir "resources/public/js/out"
                                   :asset-path "/js/out"
                                   :optimizations :none
                                   :source-map true
                                   :source-map-path "js/out"
                                   :pretty-print true}}]})
