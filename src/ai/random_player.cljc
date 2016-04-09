(ns ai.random-player
  (:require ai.game))

(defn play [game]
  (let [available (ai.game/available-moves game)]
    (assert (not-empty available)
            "Can't play if no available moves")
    (rand-nth (vec available))))
