(ns gravie-project.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [gravie-project.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
