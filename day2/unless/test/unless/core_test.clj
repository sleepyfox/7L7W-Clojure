(ns unless.core-test
  (:require [clojure.test :refer :all]
            [unless.core :refer :all]))

(deftest an-unless-macro
  (testing "(unless false 1) should be 1"
    (is (= (unless false 1) 1)))
  (testing "(unless true 1) should be nil"
    (is (= (unless true 1) nil)))
  (testing "(unless false 1 2) should be 1"
    (is (= (unless false 1 2) 1)))
  (testing "(unless true 1 2) should be 2"
    (is (= (unless true 1 2) 2)))
)