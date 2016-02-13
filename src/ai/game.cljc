(ns ai.game)

(defn think [] "I'm thinking!")

(defprotocol Game
  "Sequential, turn-based game"
  (players [g] "Set of players in the game")
  (who-won [g] "Returns player who won (or nil if in progress)")
  (next-player [g] "Returns player who will play next (or nil if game over)")
  (available-moves [g] "Returns set of available moves")
  (play [g move] "Returns new game representing result of move"))

