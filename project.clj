(defproject coffee-table "0.0.1"
  :description "Rate the Coffee joints I visit"
  :dependencies [;; Language Cores
                 [org.clojure/clojure "1.8.0"]

                 ;; Data Schema/Validation
                 [prismatic/schema "1.1.3"]

                 ;; Restartable Components
                 [com.stuartsierra/component "0.3.1"]

                 ;; Datomic
                 [com.datomic/datomic-free "0.9.5394"]
                 [io.rkn/conformity "0.4.0"]

                 ;; Om.Next
                 [org.omcljs/om "1.0.0-alpha46"]

                 ;; Web Server
                 [aleph "0.4.1"]
                 [yada "1.1.39"]]

  :source-paths ["src/cljc" "src/clj"]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}})
