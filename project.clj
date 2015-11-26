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
                 [ragtime "0.5.0"]
                 [yesql "0.5.0-rc3"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring-server "0.4.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.omcljs/om "0.9.0"]
                 [sablono "0.4.0"]
                 [om-semantic "0.1.5-SNAPSHOT"]
                 [com.andrewmcveigh/cljs-time "0.3.14"]]
  :source-paths ["src/clj" "src/sql"]
  :plugins [[lein-ring "0.9.6"]
            [lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]]
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
                                   :pretty-print true}}]}
  :aliases {"migrate" ["run" "-m" "coffee-table.db/migrate"]
            "rollback" ["run" "-m" "coffee-table.db/rollback"]})
