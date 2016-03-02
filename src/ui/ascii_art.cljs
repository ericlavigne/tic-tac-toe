(ns ui.ascii-art
  (:require [ui.clock :refer [clock seconds-passed]]
            [rum.core :as rum]
            [ui.textual :as textual]))

(defn render-board []
  (let [pieces (:pieces @textual/board)
        size (:board-size @textual/board)]
    [:table
     (for [row (range size)]
       [:tr (for [col (range size)]
              [:td (cond
                     ((:x pieces) [row col]) "x"
                     ((:o pieces) [row col]) "o"
                     :else ".")])])]))

(defn render []
  [:div
   (render-board)
   (textual/render-game-result)])

(rum/defc gomoku-app < rum/reactive []
  (rum/react clock)
  (when (textual/time-to-update-board?)
    (textual/update-board))
  (render))
