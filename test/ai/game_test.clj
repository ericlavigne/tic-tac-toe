(ns ai.game-test
  (:require [clojure.test :refer :all]
            [ai.game :as g]))

(deftest think-test
  (testing "The AI can think."
    (is (= (g/think) "I'm thinking!"))))

