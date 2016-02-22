(ns ai.minimax-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.random-player :as rand-player]
            [ai.minimax :as minimax]))

(deftest basic-competency-test
  (testing "Minimax should never lose to random"
           (is (not= :white
                     (game/determine-winner
                       (gomoku/empty-board 3 3)
                       {:black (fn [game]
                                 (minimax/play
                                   game
                                   game/evaluate-based-on-winning
                                   6))
                        :white rand-player/play})))))
