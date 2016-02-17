(ns ai.game-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.random-player :as player]))

(deftest random-player-test
  (testing "Random player can finish tic-tac-toe without exceptions"
           (game/determine-winner (gomoku/empty-board 3 3)
                                  {:black player/play
                                   :white player/play})))

(deftest generate-pairings-test
  (testing "Round with two players and two available positions requires two games"
           (is (= (game/generate-pairings #{:black :white}
                                          #{:random :brute})
                  #{{:black :random :white :brute}
                    {:black :brute :white :random}})))
  (testing "Round with three players and two available positions requires six games"
           (is (= (game/generate-pairings #{:black :white}
                                          #{:random :brute :mtdf})
                  #{{:black :random :white :brute}
                    {:black :random :white :mtdf}
                    {:black :brute :white :random}
                    {:black :brute :white :mtdf}
                    {:black :mtdf :white :random}
                    {:black :mtdf :white :brute}}))))