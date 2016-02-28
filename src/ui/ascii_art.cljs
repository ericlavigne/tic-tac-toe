(ns ui.ascii-art
  (:require [ai.game :as g]
            [ai.gomoku :as gomoku]
            [ui.clock :refer [clock seconds-passed]]
            [rum.core :as rum]
            [ai.random-player :as player]))

(defonce app-state (atom {:game (gomoku/empty-board 3 3)
                          :last-move-time @clock}))

(rum/defc gomoku-board < rum/reactive [{:keys [game players]}]
  (let [pieces (:pieces (rum/react game))]
  [:div
   [:table
    (map (fn [i]
           [:tr (map (fn [j]
                       (let [pos [i j]
                             pieces (:pieces @game)
                             piece (cond
                                     ((:x pieces) pos) "x"
                                     ((:o pieces) pos) "o"
                                     :else ".")]
                         [:td piece]))
                     (range (:board-size @game)))])
         (range (:board-size @game)))]
   [:p (str "Winner: " (g/who-won (rum/react game)))]
   ]))

(rum/defc gomoku-app < rum/reactive []
  (when (< 1
           (seconds-passed (:last-move-time @app-state)
                           (rum/react clock)))
    (swap! app-state assoc :last-move-time @clock)
    (let [game (:game @app-state)]
      (swap! app-state assoc :game
             (if (g/finished? game)
               (gomoku/empty-board 3 3)
               (g/play game
                       (player/play game))))))
  [:div
   (gomoku-board {:game (rum/cursor app-state [:game])})])
