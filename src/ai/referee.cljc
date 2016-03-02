(ns ai.referee
  (:require [ai.game :as game]
            [clojure.math.combinatorics :as combo]))

(defn generate-pairings [positions players]
  (let [positions-vec (vec positions)
        player-selections (combo/selections players
                                            (count positions-vec))
        all-pairings  (map (fn [player-selection]
                             (zipmap positions-vec
                                     player-selection))
                           player-selections)
        non-trivial-pairings (remove (fn [pairing]
                                       (apply = (vals pairing)))
                                     all-pairings)]
    (set non-trivial-pairings)))

;(defn determine-round-winner [game player-name-to-move-function]
  
  
;(defn determine-stronger [game player-name-to-move-function
;                          & {:keys [rounds threshold] :or {rounds 5 threshold 3}}]

