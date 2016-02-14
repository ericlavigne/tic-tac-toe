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

(defn consecutive-stones
  "How many pieces player has in a row
   (optionally specify position, direction)"
  ([gomoku player]
    (let [player-stones (get-in gomoku [:stones player])]
      (if (empty? player-stones)
        0
        (apply max
               (map #(consecutive-stones gomoku player %)
                    player-stones)))))
  ([gomoku player position]
    (if (not ((get-in gomoku [:stones player]) position))
      0
      (apply max
             (map (fn [dir]
                    (let [opp (vec-opposite dir)]
                      (+ 1
                         (consecutive-stones gomoku player
                                             (vec-plus position dir)
                                             dir)
                         (consecutive-stones gomoku player
                                             (vec-plus position opp)
                                             opp))))
                  half-directions))))
  ([gomoku player position direction]
    (consecutive-stones gomoku player position direction 0))
  ([gomoku player position direction found-so-far]
    (if (not ((get-in gomoku [:stones player]) position))
      found-so-far
      (recur gomoku player (vec-plus position direction)
             direction (inc found-so-far)))))

(defrecord Gomoku
  [stones ; {:black #{[0 0] [1 2]} :white #{[1 1] [2 0]}}
   available ; #{[0 1] [0 2] [0 3] [1 0] [2 1] [2 2]}
   player ; :black
   required-stones-in-a-row] ; 3
  g/Game
  (players [g] #{:black :white})
  (who-won [g] (first (filter (fn [player]
                                (<= required-stones-in-a-row
                                    (consecutive-stones g player)))
                              (g/players g))))
  (next-player [g] player)
  (available-moves [g] available)
  (play [g move]
    (assert (available move)
            "Played a move that was not available.")
    (assoc g
           :player (if (= player :black) :white :black)
           :stones (update stones player conj move)
           :available (disj (:available g) move))))

(defn empty-board [board-size required-stones-in-a-row]
  (map->Gomoku {:stones {:black #{} :white #{}}
                :available (set (map vec (combo/selections
                                           (range board-size) 2)))
                :player :black
                :board-size board-size
                :required-stones-in-a-row required-stones-in-a-row}))

