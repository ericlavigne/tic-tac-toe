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
