(ns ai.minimax-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.random-player :as rand-player]
            [ai.minimax :as minimax]))

(deftest basic-competency-test
  (testing "Minimax should never lose to random"
           (is (not= :o
                     (game/determine-winner
                       (gomoku/empty-board 3 3)
                       {:x (minimax/player game/evaluate-based-on-winning 6)
                        :o rand-player/play})))))
