(ns ui.graphic
  (:require [ui.clock :refer [clock seconds-passed]]
            [rum.core :as rum]
            [ui.textual :as textual]))

(defn render-square [position]
  (let [pieces (:pieces @textual/board)]
    [:img {:style {:width "100%" :height "100%"}
           :src (str "images/"
                     (cond
                       ((:x pieces) position) "x"
                       ((:o pieces) position) "o"
                       :else "empty")
                     ".png")}]))

(defn render-board []
  (let [size (:board-size @textual/board)]
    [:table
     (for [row (range size)]
       [:tr (for [col (range size)]
              [:td (render-square [row col])])])]))

(defn render []
  [:div
   (render-board)
   (textual/render-game-result)])

(rum/defc gomoku-app < rum/reactive []
  (rum/react clock)
  (when (textual/time-to-update-board?)
    (textual/update-board))
  (render))
