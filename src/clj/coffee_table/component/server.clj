(ns coffee-table.component.server
  (:require
   [com.stuartsierra.component :as component]
   [yada.yada :as yada]))

(defrecord YadaWebServer [routes]
  component/Lifecycle
  (start [component]
    (let [app-routes (:routes routes)]
      (assoc component :server (yada/listener app-routes {:port 3000}))))
  (stop [component]
    (when-let [server (:server component)]
      ((:close server)))
    (dissoc component :server)))

(defn new-yada-webserver []
  (map->YadaWebServer {}))
