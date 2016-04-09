(ns ai.game)

(defprotocol Game
  "Sequential, turn-based game"
  (players [g] "Set of players in the game")
  (who-won [g] "Returns player who won (or nil if in progress)")
  (next-player [g] "Returns player who will play next (or nil if game over)")
  (available-moves [g] "Returns set of available moves")
  (play [g move] "Returns new game representing result of move"))

(defn finished? [game]
  (or (who-won game)
      (empty? (available-moves game))))

(defn determine-winner [game player-to-move-function]
  (loop [g game]
    (let [winner (who-won g)]
      (if winner winner
        (if (empty? (available-moves g))
          nil
          (let [move-function ((next-player g) player-to-move-function)]
            (recur (play g (move-function g)))))))))

(defn evaluate-based-on-winning [game perspective]
  (let [winner (who-won game)]
    (cond
      (nil? winner) 0
      (= winner perspective) 1
      :else -1)))

(defn play-moves [game moves]
  (if (empty? moves)
    game
    (recur (play game (first moves))
           (rest moves))))


