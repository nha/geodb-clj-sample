(ns geodb-clj-sample.core-test
  (:require [clojure.test :refer :all]
            [geodb.driver :as geodb]
            [geodb.api]))

(deftest geodb-test
  (testing "geodb is awesome"
    (is (true? geodb.api/awesome))))
