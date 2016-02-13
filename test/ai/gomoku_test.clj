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

