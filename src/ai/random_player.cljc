(ns ai.random-player
  (:require [ai.game :as g]))

(defn play [game]
  (let [available (g/available-moves game)]
    (assert (not-empty available)
            "Can't play if no available moves")
    (rand-nth (vec available))))
