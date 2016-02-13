(ns ai.gomoku
  (:require [ai.game :as g]
            [clojure.math.combinatorics :as combo]))

(defrecord Gomoku
  [black-stones white-stones available player required-stones-in-a-row]
  g/Game
  (players [g] #{:black :white})
  (who-won [g] nil) ; TODO: win calculation
  (next-player [g] player)
  (available-moves [g] available)
  (play [g move]
    (let [player-stone-key (if (= (g/next-player g)
                                  :black)
                             :black-stones :white-stones)]
      (assoc g
        player-stone-key (conj (player-stone-key g) move)
        :available (disj (:available g) move)))))

(defn empty-board [board-size required-stones-in-a-row]
  (map->Gomoku {:black-stones #{} :white-stones #{}
                :available (set (map vec (combo/selections
                                           (range board-size) 2)))
                :player :black
                :board-size board-size
                :required-stones-in-a-row required-stones-in-a-row}))

