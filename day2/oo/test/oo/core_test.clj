(ns oo.core-test
  (:require [clojure.test :refer :all]
            [oo.core :refer :all]))

(defprotocol Animal
  (make-noise [this]))

(defrecord Cat [name]
  Animal
  (make-noise [_] (str name " goes meow")))

(deftest a-Cat-record
  (testing "A cat should go meow"
    (def my-cat(Cat. "Sherbet"))
    (is (= (make-noise my-cat) "Sherbet goes meow" ))))

(defprotocol Account
  (get-balance [this])
  (deposit [this amount]))

(defrecord BankAccount [owner balance]
  Account
  (get-balance [this] (:balance this))
  (deposit [this amount] 
    (assoc this :balance (+ (:balance this) amount))))

(defn new-bank-account [owner-name] (BankAccount. owner-name 0))

(deftest a-bank-account
  (testing "A bank account when opened should have a zero balance"
    (def my-bank-account (new-bank-account "Me"))
    (is (= (get-balance my-bank-account) 0)))
  (testing "Adding 10 to a new bank account should leave balance 10"
    (def another-account (new-bank-account "Myself"))
    (def changed-account (deposit another-account 10))
    (is (= (get-balance changed-account) 10))))

