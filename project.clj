(defproject geodb-clj-sample "0.1.0-SNAPSHOT"
  :description "Example app using GeoDB in Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [io.geodb/geodb-clj-sdk "0.0.1-SNAPSHOT"]]
  :main ^:skip-aot geodb-clj-sample.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
