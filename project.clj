(defproject coffee-table "0.0.1"
  :description "A site for reviewing cafés"
  :url "http://github.com/RobotDisco/coffee-table"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.omcljs/om "1.0.0-alpha32" :exclusions [cljsjs/react]]
                 [sablono "0.6.3"]
                 [com.andrewmcveigh/cljs-time "0.4.0"]
                 [cljs-react-test "0.1.3-SNAPSHOT"]
                 [cljsjs/react-with-addons "0.14.3-0"]
                 [cljsjs/semantic-ui "2.1.8-0"]
                 [devcards "0.2.1-6"]]
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.0-4"]
            [lein-doo "0.1.6"]]
  :cljsbuild {:builds [{:id "dev"
                        :figwheel true
                        :source-paths ["src/cljs"]
                        :compiler {:main coffee-table.core
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/coffee_table.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map-timestamp true}}
                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :compiler {:output-to "resources/public/js/compiled/test.js"
                                   :output-dir "resources/public/js/compiled/test_out"
                                   :main coffee-table.test.runner
                                   :optimizations :none}}
                       {:id "cards"
                        :source-paths ["src/cljs"]
                        :figwheel {:devcards true}
                        :compiler {:main coffee-table.core
                                   :asset-path "js/compiled/cards_out"
                                   :output-to "resources/public/js/compiled/cards.js"
                                   :output-dir "resources/public/js/compiled/cards_out"
                                   :source-map-timestamp true}}]}
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.0-4"]]
                   :source-paths ["src/cljs"]}})
