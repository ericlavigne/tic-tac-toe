(ns ai.minimax
  (:require [ai.game :as g]))

; evaluator is fn[game,perspective]->float
(defn evaluate-with-depth [game evaluator perspective depth]
  (let [available (g/available-moves game)]
    (if (or (<= depth 0) (empty? available))
      (evaluator game perspective)
      (let [maximizing (= perspective (g/next-player game))
            possible-scores (map (fn [move]
                                   (evaluate-with-depth
                                     (g/play game move)
                                     evaluator perspective
                                     (dec depth)))
                                 available)]
        (apply (if maximizing
                 max
                 min)
               possible-scores)))))
  
(defn play [game evaluator depth]
  (let [available (g/available-moves game)
        player (g/next-player game)]
    (assert (not-empty available)
            "Can't play if no available moves")
    (apply max-key
           (fn [move] (evaluate-with-depth
                        (g/play game move)
                        evaluator player
                        (dec depth)))
           available)))
