(ns ai.game)

(defn think [] "I'm thinking!")

(defprotocol Game
  "Sequential, turn-based game"
  (players [g] "Set of players in the game")
  (who-won [g] "Returns player who won (or nil if in progress)")
  (next-player [g] "Returns player who will play next (or nil if game over)")
  (available-moves [g] "Returns set of available moves")
  (play [g move] "Returns new game representing result of move"))

(defn determine-winner [game player-to-move-function]
  (loop [g game]
    (let [winner (who-won g)]
      (if winner winner
        (if (empty? (available-moves g))
          nil
          (let [move-function ((next-player g) player-to-move-function)]
            (recur (play g (move-function g)))))))))

;(defn determine-round-winner [game player-name-to-move-function]

;(defn determine-stronger [game player-name-to-move-function
;                          & {:keys [rounds threshold] :or {rounds 5 threshold 3}}]
