(ns ai.game-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.random-player :as player]))

(deftest random-player-test
  (testing "Random player can finish tic-tac-toe without exceptions"
           (game/determine-winner (gomoku/empty-board 3 3)
                                  {:x player/play
                                   :o player/play})))

(deftest evaluate-based-on-winning-test
  (let [game-start (gomoku/empty-board 3 3)
        game-in-progress (game/play-moves game-start
                                          [[0 0] [1 0]
                                           [0 1] [1 1]])
        black-wins (game/play game-in-progress [0 2])]
    (testing "Game still in progress evaluates 0 for everyone"
             (is (= 0 (game/evaluate-based-on-winning game-start :x)))
             (is (= 0 (game/evaluate-based-on-winning game-in-progress :o))))
    (testing "Black wins - eval to 1 for black or -1 for white"
             (is (= 1 (game/evaluate-based-on-winning black-wins :x)))
             (is (= -1 (game/evaluate-based-on-winning black-wins :o))))))
