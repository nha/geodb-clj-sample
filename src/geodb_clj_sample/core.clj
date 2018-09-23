(ns geodb-clj-sample.core
  (:require [geodb.config]
            [geodb.protocol]
            [geodb.driver :as driver]
            [geodb.create]
            [geodb.api :as geodb]))

(defn -main
  [& args]
  (println "Hello, GeoDB! " {:awesome geodb.api/awesome
                             :version geodb.api/version
                             :commitVersion geodb.api/commitVersion
                             :buildNum geodb.api/buildNum})
  (println "Open the source file and REPL in to follow the tutorial"))


(comment
  ;;
  ;; Evaluate these forms in your REPL
  ;;

  (geodb.api/init {:host (or (System/getenv "GEODB_API_HOST") "geodb.io")
                   :type :ws
                   :protocol :https})

  (geodb.api/on "ready"
                (fn [evt]
                  (println "ready event" evt)))

  (geodb.api/on "error" (fn [evt]
                          (println "error event" evt)))

  (geodb.api/on "disconnect" (fn [evt]
                               (println "disconnected event")))

  (geodb.api/connect {:UserToken (java.util.UUID/fromString
                                   (System/getenv "GEODB_USER_TOKEN"))
                      :ApiKey (java.util.UUID/fromString
                                (System/getenv "GEODB_API_KEY"))})

  (def channel (str "#test-" (System/currentTimeMillis)))

  (geodb.api/subscribe {:channel channel}
                       (fn [err data metadata]
                         (println "data received on channel #test" err data metadata)))


  (def res (geodb.api/publish {:channel channel
                               :payload {:ok "true"}}
                              (fn publish-callback [err data metadata]
                                (println "data sent on channel #test" err data metadata))))


  )


;; This concludes the tutorial :)
;; You may want to look at the tests too, they include some potentially
;; interesting patterns
;;
;; (def driver (geodb.create/make {:host (or (System/getenv "GEODB_API_HOST") "geodb.io")
;;                                 :type :ws
;;                                 :protocol :https}))
