(ns geodb-clj-sample.core
  (:gen-class)
  (:require [geodb.driver :as geodb]))

;;
;; Evaluate these in the REPL to get a feel of how this works
;;


;; we will subscribe to a specific channel in a small area of London

(def  subscribe-params {:channel "#beer"
                        :location {:lat 51.49553
                                   :lon -0.19506
                                   :radius "10km"
                                   :annotation "Lexham Gardens"}})

;; we will publish from something close enough
(def publish-params {:channel "#beer"
                     :location {:lat 51.4779
                                :lon -0.1311
                                :annotation "Clapham North"}
                     :payload {:msg "Drinks?"}})

;;
;; This is the most important: is represents a connection to the servers
;;
(def driver (geodb/make-driver {:host (or (System/getenv "GEODB_API_HOST") "api.geodb.io")}))

;; Register a bunch of callbacks
(def connect-p (promise))
(def driver (-> driver
                (geodb/on "connect" (fn [evt]
                                      (println "Connected")))
                (geodb/on "error" (fn [evt]
                                    (println "Error")))
                (geodb/on "disconnect" (fn [evt]
                                         (println "Disconnected")))))

;; Now the driver will connect
(def driver (geodb/connect driver {:UserToken (System/getenv "GEODB_USER_TOKEN")
                                   :ApiKey (System/getenv "GEODB_API_KEY")
                                   :protocol :http ;; TODO remove
                                   }))

(defn -main
  [& args]
  (println "Hello, GeoDB!")

  ;; TODO with-open

  (let [

        ;;
        ;; register a bunch of callback events
        ;;



        ;;
        ;; connect
        ;; note: the Driver is a simple Record, and is therefore immutable
        ;;

        ]


    ;; subscribe


    ;; publish

    ;; get result!


    ;; close
    )
  )


(comment

  ;;
  ;; here is a GeoDB Clojure tutorial
  ;;






  )
