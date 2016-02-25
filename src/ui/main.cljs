(ns ui.main
  (:require [ai.game :as g]
            [rum.core :as rum]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(rum/defc gomoku-app < rum/static [title]
  (println "Rendering gomoku-app")
  [:h1 "Gomoku"])

(rum/mount (gomoku-app) (js/document.getElementById "app"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
