(ns ai.connect4-test
  (:require [clojure.test :refer :all]
            [ai.game :as game]
            [ai.connect4 :as connect4]))

(deftest connect4-availability
  (testing "Filling up column makes it unavailable"
    (let [move 2
          before-move (connect4/empty-board 3 3 3)
          after-two-moves (game/play-moves before-move [move move])
          after-three-moves (game/play-moves before-move [move move move])]
      (is ((game/available-moves before-move) move)
        "Move is available before playing it.")
      (is ((game/available-moves after-two-moves) move)
        "Move is available after playing it twice.")
      (is ((game/available-moves after-three-moves) move)
        "Three moves fills the column - no longer available."))))

(deftest connect4-victory
  (testing "Win when one player has three in a row"
           (let [game (connect4/empty-board 3 3 3)]
             (is (nil? (game/who-won game))
               "Empty board has no winner.")
             (is (nil? (game/who-won
                         (game/play-moves game [0 0 1])))
               "2 in a row is not enough.")
             (is (= :x (game/who-won
                         (game/play-moves game [0 0 1 1 2])))
               "Check that X can win by getting 3 in a row.")
             (is (= :o (game/who-won
                         (game/play-moves game [0 0 1 1 0 2 1 2])))
               "O can win if X plays two dumb moves in a row."))))
               