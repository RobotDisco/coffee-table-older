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
                 [ring/ring-json "0.4.0"]]
  :source-paths ["src/clj" "src/sql"]
  :plugins [[lein-ring "0.9.6"]
            [org.clojars.punkisdead/lein-cucumber "1.0.4"]]
  :ring {:handler coffee-table.core/app
         :nrepl {:start? true
                 :port 3666}}
  :profiles {:dev {:dependencies [[ring-server "0.4.0"]
                                  [clj-http "2.0.0"]]}}
  :aliases {"migrate" ["run" "-m" "coffee-table.db/migrate"]
            "rollback" ["run" "-m" "coffee-table.db/rollback"]})
