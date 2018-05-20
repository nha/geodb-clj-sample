(ns geodb-clj-sample.core
  (:gen-class)
  (:require [geodb.driver :as geodb]))

(defn -main
  [& args]
  (println "Hello, GeoDB!")
  (println "Open the source file and REPL in to follow the tutorial")
  )

;;
;; A driver represents a connection to the servers
;;

(def driver (geodb/make-driver {:host (or (System/getenv "GEODB_API_HOST") "api.geodb.io")
                                :type :ws
                                :protocol :http ;; TODO :https
                                :packer :edn
                                :ws-kalive-ms 20000
                                :callback-timeout 10000}))

(def connect-p (promise))

;; Register event handlers
(def driver (-> driver
                (geodb/on "connect" (fn [evt]
                                      (println "connect event")
                                      (deliver connect-p evt)))
                (geodb/on "error" (fn [evt]
                                    (println "error event")))
                (geodb/on "disconnect" (fn [evt]
                                         (println "disconnected event")))))

(comment
  (do

    ;; Now the driver will connect
    (def driver (geodb/connect driver {:UserToken (java.util.UUID/fromString
                                                    (System/getenv "GEODB_USER_TOKEN"))
                                       :ApiKey (java.util.UUID/fromString
                                                 (System/getenv "GEODB_API_KEY"))}))

    (deref connect-p)
    (println "Connected? " (:open? (geodb/connection-state driver))) ;; true

    (println "Auth-state" (geodb/auth-status driver))

    (def driver (geodb/subscribe driver {:channel "#test"}
                                 (fn [err data metadata]
                                   (println "data received on channel #test" err data metadata))))

    (def driver (geodb/publish driver {:channel "#test"
                                       :payload {:msg "anything goes in the payload"}}
                               (fn publish-callback [err data metadata]
                                 (println "publish " err "-" data "-" metadata))))


    ;; try different variations now!

    (def driver (geodb/subscribe driver {:channel "#test"
                                         :location {:lat 48.8566
                                                    :lon 2.3522
                                                    :radius "50km"
                                                    :annotation "Paris"}}
                                 (fn [err data metadata]
                                   (println "data received in Paris#test" err data metadata))))

    (def driver (geodb/publish driver {:channel "#test"
                                       :location {:lat 48.8049
                                                  :lon 2.1204
                                                  ;;:radius "5km"
                                                  :annotation "Versailles"}
                                       :payload {:msg "anything goes in the payload"}}
                               (fn publish-callback [err data metadata]
                                 (def err err)
                                 (def data data)
                                 (def metadata metadata)
                                 (println "publish " err "-" data "-" metadata))))
    )

  ;; try these in you REPL!
  (keys (geodb/list-subscriptions driver))
  )
