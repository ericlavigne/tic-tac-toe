(ns ui.main
  (:require [ai.game :as g]
            [ai.gomoku :as gomoku]
            [rum.core :as rum]
            [ai.random-player :as player]))

(enable-console-print!)

(defn now []
  (.getTime (js/Date.)))

(def clock (atom (now)))

(defn tick []
  (reset! clock (now))
  (js/setTimeout tick 100))
(tick)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:game (gomoku/empty-board 3 3)
                          :last-move-time (now)}))

(rum/defc gomoku-board < rum/reactive [{:keys [game players]}]
  (let [pieces (:pieces (rum/react game))]
  [:div
   [:p "A gomoku-board"]
   [:p (str "Pieces: " pieces)]
   [:p (str "Winner: " (g/who-won (rum/react game)))]
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
   [:p "View code on "
    [:a {:href "http://github.com/ericlavigne/gomoku"} "Github"]]
   ]))

(rum/defc gomoku-app < rum/reactive []
  ;(println "Rendering gomoku-app")
  (when (< (+ 1000 (:last-move-time @app-state))
           (rum/react clock))
    (swap! app-state assoc :last-move-time (now))
    (let [game (:game @app-state)]
      (swap! app-state assoc :game
             (if (g/finished? game)
               (gomoku/empty-board 3 3)
               (g/play game
                       (player/play game))))))
  [:div
   [:h1 "Gomoku"]
   (gomoku-board {:game (rum/cursor app-state [:game])})])

(rum/mount (gomoku-app) (js/document.getElementById "app"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
