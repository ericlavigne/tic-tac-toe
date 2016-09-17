(ns ai.connect4
  (:require [ai.game :as game]
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
  ([g player]
   (let [player-pieces (get-in g [:pieces player])]
     (if (empty? player-pieces)
       0
       (apply max
              (map #(consecutive-pieces g player %)
                   player-pieces)))))
  ([g player position]
   (if (not ((get-in g [:pieces player]) position))
     0
     (apply
       max
       (map (fn [dir]
              (let [opp (vec-opposite dir)]
                (+ 1
                   (consecutive-pieces g player
                                       (vec-plus position dir)
                                       dir)
                   (consecutive-pieces g player
                                       (vec-plus position opp)
                                       opp))))
            half-directions))))
  ([g player position direction]
   (consecutive-pieces g player position direction 0))
  ([g player position direction found-so-far]
   (if (not ((get-in g [:pieces player]) position))
     found-so-far
     (recur g player (vec-plus position direction)
            direction (inc found-so-far)))))

(defrecord Connect4
  [pieces ; {:x #{[0 0] [1 2]} :o #{[1 1] [2 0]}}
   available ; unfilled columns #{0 1 2 3 5 6}
   column-heights ; [0 0 0 3 6 1 4]
   player ; :x
   board-width ; 7
   board-height ; 6
   required-pieces-in-a-row] ; 4
  game/Game
  (players [g] #{:x :o})
  (who-won [g] (first
                 (filter (fn [player]
                           (<= required-pieces-in-a-row
                               (consecutive-pieces g player)))
                         (game/players g))))
  (next-player [g] player)
  (available-moves [g]
    (vec (filter
           #(< (column-heights %) 6)
           (range board-width))))
  (play [g move-col]
    (assert ((game/available-moves g) move-col)
            "Played a move that was not available.")
    (let [move-row (inc (column-heights move-col))]
      (assoc g
        :player (if (= player :x) :o :x)
        :pieces (update pieces player conj [move-col move-row])
        :column-heights (vec (update column-heights move-col inc))))))

(defn empty-board [board-width board-height required-pieces-in-a-row]
  (map->Connect4 {:pieces {:x #{} :o #{}}
                  :column-heights (vec (repeat board-width 0))
                  :player :x
                  :board-width board-width
                  :board-height board-height
                  :required-pieces-in-a-row
                  required-pieces-in-a-row}))
