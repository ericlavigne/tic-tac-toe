(ns ai.epsilon-greedy
  (:require ai.game))

(def memory (atom {}))

; evaluator is fn[game,perspective]->float
(defn evaluate-move-with-deja-vu [game perspective]
  (if (ai.game/finished? game)
    (ai.game/evaluate-based-on-winning game perspective)
    (get @memory game)))
  
(defn evaluate-with-depth [game evaluator perspective depth]
  (let [available (ai.game/available-moves game)]
    (if (or (<= depth 0) (empty? available))
      (evaluator game perspective)
      (let [maximizing (= perspective (ai.game/next-player game))
            possible-scores (map (fn [move]
                                   (evaluate-with-depth
                                     (ai.game/play game move)
                                     evaluator perspective
                                     (dec depth)))
                                 available)]
        (apply (if maximizing
                 max
                 min)
               possible-scores)))))
  
(defn play [game evaluator depth]
  (let [available (ai.game/available-moves game)
        player (ai.game/next-player game)]
    (assert (not-empty available)
            "Can't play if no available moves")
    (apply max-key
           (fn [move] (evaluate-with-depth
                        (ai.game/play game move)
                        evaluator player
                        (dec depth)))
           available)))

(defn play [game previous-move])

(defn player []
  (let [previous-move (atom nil)]
    (fn [game]
      (reset! previous-move
        (play game previous-move)))))
