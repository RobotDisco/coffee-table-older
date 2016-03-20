(defproject coffee-table "0.0.1"
  :description "A site for reviewing cafés"
  :url "http://github.com/RobotDisco/coffee-table"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.omcljs/om "1.0.0-alpha31" :exclusions [cljsjs/react]]
                 [com.andrewmcveigh/cljs-time "0.4.0"]
                 [cljs-react-test "0.1.3-SNAPSHOT"]
                 [cljsjs/react-with-addons "0.14.3-0"]]
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.0-4"]
            [lein-doo "0.1.6"]]
  :cljsbuild {
              :builds [{:id "dev"
                        :figwheel true
                        :source-paths ["src/cljs"]
                        :compiler {
                                   :main coffee-table.core
                                   :asset-path "js"
                                   :output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js"
                                   :optimizations :none
                                   :pretty-print true}}
                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :compiler {:output-to "resources/public/js-test/test.js"
                                   :output-dir "resources/public/js-test"
                                   :main coffee-table.test.runner
                                   :optimizations :none}}]}
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.0-4"]]
                   :source-paths ["src/cljs"]}})
