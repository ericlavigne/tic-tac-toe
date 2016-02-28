(ns ai.referee-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.referee :as referee]))

(deftest generate-pairings-test
  (testing "Round with two players and two available positions requires two games"
           (is (= (referee/generate-pairings #{:x :o}
                                             #{:random :brute})
                  #{{:x :random  :o :brute}
                    {:x :brute   :o :random}})))
  (testing "Round with three players and two available positions requires six games"
           (is (= (referee/generate-pairings #{:x :o}
                                             #{:random :brute :mtdf})
                  #{{:x :random  :o :brute}
                    {:x :random  :o :mtdf}
                    {:x :brute   :o :random}
                    {:x :brute   :o :mtdf}
                    {:x :mtdf    :o :random}
                    {:x :mtdf    :o :brute}}))))
