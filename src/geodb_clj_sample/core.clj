(ns geodb-clj-sample.core
  (:require [geodb.api :as geodb]
            [geodb.config]
            [geodb.create]
            [geodb.driver :as driver]
            [geodb.protocol]
            [geodb.reader]))

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

  (geodb.api/init {})

  (geodb.api/on "ready"
                (fn [evt]
                  (println "ready event" evt)))

  (geodb.api/on "error" (fn [evt]
                          (println "error event" evt)))

  (geodb.api/on "disconnect" (fn [evt]
                               (println "disconnected event")))

  (def connect-res @(geodb.api/connect {:UserToken (java.util.UUID/fromString (System/getenv "GEODB_USER_TOKEN"))
                                        :ApiKey (java.util.UUID/fromString (System/getenv "GEODB_API_KEY"))}))

  (def channel (str "#test-" (System/currentTimeMillis)))

  (def sub-res @(geodb.api/subscribe {:channel channel}
                                  (fn [err data metadata]
                                    (println "data received on channel #test" err data metadata))))

  (def subscription-id (get-in sub-res [:data :id]))

  (def pub-res @(geodb.api/publish {:channel channel
                                    :payload {:ok "true"}}
                                   (fn publish-callback [err data metadata]
                                     (println "data sent on channel #test" err data metadata))))

  (def r @(geodb.api/reader {:subscriptionId (get-in sub-res [:data :id])
                             :startMessageId "earliest"}))

  ;; replay messages from the beginning
  (while (:data @(geodb.reader/hasMessageAvailable r))
    (println "message " @(geodb.reader/readNext r)))

  @(geodb.reader/close r)

  )


;; This concludes the tutorial :)
;; You may want to look at the tests too, they include some potentially
;; interesting patterns
;;
;; (def driver (geodb.create/make {:host (or (System/getenv "GEODB_API_HOST") "geodb.io")
;;                                 :type :ws
;;                                 :protocol :https}))
