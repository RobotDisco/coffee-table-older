(require '[clj-http.client :as client])
(require '[clojure.data.json :as json])
(require '[clojure.test :refer :all])
(require '[coffee-table.dev :refer [server-start]])


(def +server+ "http://localhost:3000")
(def world (atom {:server nil
                  :response nil}))


(Given #"^the server is running$" []
       (swap! world assoc :server (server-start)))

(When #"^I list the visits$" []
      (swap! world assoc :response
             (client/get (clojure.string/join (list +server+ "/visits")))))

(When #"^I create a visit with:" []
      (swap! world assoc :response
             (client/post (clojure.string/join (list +server+ "/visits"))
                          {:body "{}" :content-type "application/json"})))

(Then #"^I get a response code of (\d+)$" [code]
      (assert (= (Integer. code)
                 (-> @world :response :status))))

(Then #"^I get back JSON$" []
      (assert (= "application/json"
                (-> @world :response :headers :content-type))))

(Then #"^my response includes a \"(.+)\" header$" [key]
      (is (contains? (-> @world :response :headers) key)))
