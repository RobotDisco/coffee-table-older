(ns user
  "Tools for interactive development with the REPL. This file should not be included in production builds of the application."
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all]]
   [com.stuartsierra.component :as component]
   [coffee-table.system :refer [dev-system]]
   #_ "insert main namespace here"))

(def system
  "A Var containing an object representing the application under development"
  nil)

(defn init
  "Creates an initializes the system under development in the Var #'system."
  []
  (alter-var-root #'system (fn [_]
                             (dev-system {:db-uri "datomic:mem://coffee-table"}))))

(defn start
  "Starts the system running, updates the Var #'system."
  []
  (alter-var-root #'system component/start))

(defn stop
  "Stops the system if it is currently running, updates the Var #'system."
  []
  (alter-var-root #'system component/stop))

(defn go
  "Initializes and starts the system running."
  []
  (init)
  (start)
  :ready)

(defn reset
  "Stops the system, reloads modified source files, and restarts it."
  []
  (stop)
  (refresh :after `go))
