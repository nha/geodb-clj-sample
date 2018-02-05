(ns geodb-clj-sample.core
  (:gen-class)
  (:require [geodb.driver :as geodb]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, GeoDB!")

  ;; TODO with-open

  (let [driver (geodb/make-driver
                 {:host      "api.geodb.io"        ;; default

                  ;; TODO use these
                  :reconnect-timeout 5000
                  :exp-backoff-ceiling 60000

                  ;; TODO data format to get back
                  :packer :edn      ;; default to EDN
                  :type :ws         ;; only type supported for now
                  :protocol :https  ;; secure by default
                  :callback-timeout 10000}
                 )]

    ;; connect
    ;; note: the Driver is a simple Record, and is therefore immutable
    ;;
    (geodb/connect driver
                   {:UserId    "<your-user-id-here>"
                    :ApiKey    "<you-api-key-here>"})

    ;; subscribe


    ;; publish

    ;; get result!


    ;; close
    ))
