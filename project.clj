(defproject net.robot-disco/coffee-table "0.0.1-SNAPSHOT"
  :description "A Coffeeshop logger/review webapp"
  :url "http://github.com/RobotDisco/coffee-table"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [liberator "0.13"]
                 [compojure "1.4.0"]
                 [ragtime "0.5.0"]
                 [yesql "0.5.0-rc3"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [org.clojure/clojurescript "1.7.48"]
                 [org.omcljs/om "0.9.0"]]
  :source-paths ["src/clj" "src/sql"]
  :plugins [[lein-ring "0.9.6"]
            [lein-cljsbuild "1.0.6"]]
  :ring {:handler coffee-table.dev/app-debug
         :nrepl {:start? true
                 :port 3666}}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :compiler {:main coffee-table.core
                                   :output-to "resources/public/js/out/app.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none
                                   :source-map true
                                   :pretty-print true}}]}
  :aliases {"migrate" ["run" "-m" "coffee-table.db/migrate"]
            "rollback" ["run" "-m" "coffee-table.db/rollback"]})
