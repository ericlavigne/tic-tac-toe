(ns ui.human
  (:require [ai.game :as game]
            [ai.gomoku :as gomoku]
            [ai.random-player :as random-player]
            [ui.clock :refer [clock seconds-passed]]
            [rum.core :as rum]))

(defonce players (atom {:x {:name "Human"
                            :move-fn :human}
                        :o {:name "Random"
                            :move-fn random-player/play}}))

(defonce board (atom (gomoku/empty-board 3 3)))

(defonce last-move-time (atom @clock))

(defn pause-seconds []
  (if (game/finished? @board)
    3 1))

(defn human-turn? []
  (= :human
     (get-in @players
             [(game/next-player @board)
              :move-fn])))

(defn time-to-update-board? []
  (and (not (human-turn?))
       (< (pause-seconds)
          (seconds-passed @last-move-time @clock))))

(defn update-board []
  (reset! board
          (if (game/finished? @board)
            (gomoku/empty-board 3 3)
            (game/play @board (random-player/play @board))))
  (reset! last-move-time @clock))

(defn click-position [position]
  (when (and (human-turn?)
             ((game/available-moves @board) position))
    (reset! last-move-time @clock)
    (reset! board
            (game/play @board position))))

(defn render-square [position]
  (let [pieces (:pieces @board)]
    [:img {:style {:width "100%" :height "100%"}
           :on-click #(click-position position)
           :src (str "images/"
                     (cond
                       ((:x pieces) position) "x"
                       ((:o pieces) position) "o"
                       :else "empty")
                     ".png")}]))

(defn render-board []
  (let [size (:board-size @board)]
    [:table
     (for [row (range size)]
       [:tr (for [col (range size)]
              [:td (render-square [row col])])])]))

(defn render-game-result []
  (when (game/finished? @board)
    [:p [:b (if-let [winner (game/who-won @board)]
              (str ({:x "X" :o "O"} winner)
                   " won!")
              "Tie Game")]]))

(defn render []
  [:div
   (render-board)
   (render-game-result)])

(rum/defc gomoku-app < rum/reactive []
  (rum/react clock)
  (when (time-to-update-board?)
    (update-board))
  (render))
