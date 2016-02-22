(ns ai.referee-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.referee :as referee]))

(deftest generate-pairings-test
  (testing "Round with two players and two available positions requires two games"
           (is (= (referee/generate-pairings #{:black :white}
                                             #{:random :brute})
                  #{{:black :random :white :brute}
                    {:black :brute :white :random}})))
  (testing "Round with three players and two available positions requires six games"
           (is (= (referee/generate-pairings #{:black :white}
                                             #{:random :brute :mtdf})
                  #{{:black :random :white :brute}
                    {:black :random :white :mtdf}
                    {:black :brute :white :random}
                    {:black :brute :white :mtdf}
                    {:black :mtdf :white :random}
                    {:black :mtdf :white :brute}}))))
