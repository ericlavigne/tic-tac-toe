(ns ai.gomoku-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]))

(deftest tic-tac-toe-scripted
  (testing "Playing move makes it unavailable"
    (let [move [1 2]
          before-move (gomoku/empty-board 3 3)
          after-move (game/play before-move move)]
      (is ((game/available-moves before-move) move) "Move is available before playing it.")
      (is (not ((game/available-moves after-move) move)) "Move is not available after playing it."))))

(deftest tic-tac-toe-victory
  (testing "Win when one player has three in a row"
           (loop [game (gomoku/empty-board 3 3)
                  remaining-moves [[1 1] [1 2] [2 2] [0 0] [2 0] [0 2] [2 1]]]
             (if (empty? remaining-moves)
               (is (= :black (game/who-won game))
                   "Black should win this game")
               (do (is (nil? (game/who-won game))
                       "Should be no winner when game still in progress")
                 (recur (game/play game (first remaining-moves))
                        (rest remaining-moves)))))))
