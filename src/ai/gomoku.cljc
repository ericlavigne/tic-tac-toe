(ns ai.gomoku
  (:require [ai.game :as g]
            [clojure.math.combinatorics :as combo]))

(def half-directions
  #{[0 1] [1 0] [-1 1] [1 1]})

(defn vec-plus [x y]
  [(+ (x 0) (y 0))
   (+ (x 1) (y 1))])

(defn vec-opposite [x]
  [(* -1 (x 0))
   (* -1 (x 1))])

(defn consecutive-pieces
  "How many pieces player has in a row
   (optionally specify position, direction)"
  ([gomoku player]
    (let [player-pieces (get-in gomoku [:pieces player])]
      (if (empty? player-pieces)
        0
        (apply max
               (map #(consecutive-pieces gomoku player %)
                    player-pieces)))))
  ([gomoku player position]
    (if (not ((get-in gomoku [:pieces player]) position))
      0
      (apply max
             (map (fn [dir]
                    (let [opp (vec-opposite dir)]
                      (+ 1
                         (consecutive-pieces gomoku player
                                             (vec-plus position dir)
                                             dir)
                         (consecutive-pieces gomoku player
                                             (vec-plus position opp)
                                             opp))))
                  half-directions))))
  ([gomoku player position direction]
    (consecutive-pieces gomoku player position direction 0))
  ([gomoku player position direction found-so-far]
    (if (not ((get-in gomoku [:pieces player]) position))
      found-so-far
      (recur gomoku player (vec-plus position direction)
             direction (inc found-so-far)))))

(defrecord Gomoku
  [pieces ; {:x #{[0 0] [1 2]} :o #{[1 1] [2 0]}}
   available ; #{[0 1] [0 2] [0 3] [1 0] [2 1] [2 2]}
   player ; :x
   board-size ; 3
   required-pieces-in-a-row] ; 3
  g/Game
  (players [g] #{:x :o})
  (who-won [g] (first (filter (fn [player]
                                (<= required-pieces-in-a-row
                                    (consecutive-pieces g player)))
                              (g/players g))))
  (next-player [g] player)
  (available-moves [g] available)
  (play [g move]
    (assert (available move)
            "Played a move that was not available.")
    (assoc g
           :player (if (= player :x) :o :x)
           :pieces (update pieces player conj move)
           :available (disj (:available g) move))))

(defn empty-board [board-size required-pieces-in-a-row]
  (map->Gomoku {:pieces {:x #{} :o #{}}
                :available (set (map vec (combo/selections
                                           (range board-size) 2)))
                :player :x
                :board-size board-size
                :required-pieces-in-a-row required-pieces-in-a-row}))

