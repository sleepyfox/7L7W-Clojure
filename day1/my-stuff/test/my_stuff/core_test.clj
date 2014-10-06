(ns my-stuff.core-test
  (:require [clojure.test :refer :all]
            [my-stuff.core :refer :all]))

(defn big  
  "A string length comparator, returns false if length of st more than n"
  [st n]
  (> (count st) n))

(deftest a-string-length-comparator
  (testing "should be false for the empty string"
    (is (= (big "" 0) false)))
  (testing "A string 'a' should have length > 0"
    (is (= (big "a" 0) true)))
  (testing "'cat' should have more than two letters"
    (is (= (big "cat" 2) true)))
  (testing "'cat' should not have more than 3 letters"
    (is (= (big "cat" 3) false)))
)

(defn collection-type [col] 
  (if (map? col) :map
    (if (list? col) :list 
      (if (vector? col) :vector 
        "Unmatched type"))))

(deftest a-type-checker
  (testing "should recognise the empty list as :list"
    (is (= (collection-type (list)) :list)))
  (testing "should recognise a list with one item as a list"
    (is (= (collection-type (list 1)) :list)))
  (testing "should recognise the empty vector as a vector"
    (is (= (collection-type [])) :vector))
  (testing "should recognise a vector with one item as a vector"
    (is (= (collection-type [1]) :vector)))
  (testing "should recognise the empty map as a map"
    (is (= (collection-type {}) :map)))
  (testing "should recognies a non-empty map as a map"
    (is (= (collection-type {:a 1}) :map)))
  (testing "should not recognise a set"
    (is (= (collection-type #{}) "Unmatched type")))
)