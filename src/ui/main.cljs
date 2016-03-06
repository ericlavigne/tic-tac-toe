(ns ui.main
  (:require [ui.textual :as textual]
            [ui.ascii_art :as ascii]
            [ui.graphic :as graphic]
            [ui.human :as human]
            [ui.crude-menu :as crude-menu]
            [rum.core :as rum]))

(def all-examples
  [{:name "Textual" :component textual/gomoku-app}
   {:name "Ascii Art" :component ascii/gomoku-app}
   {:name "Graphic" :component graphic/gomoku-app}
   {:name "Human" :component human/gomoku-app}
   {:name "Crude Menu" :component crude-menu/gomoku-app}])

(defonce selected (atom (first all-examples)))

(rum/defc menu < rum/reactive []
  (rum/react selected)
  [:div
   [:div (for [example all-examples]
           [:button {:on-click #(reset! selected example)}
            (:name example)])]
   [:br] [:hr]
   ((:component @selected))
   [:br] [:hr]
   [:p "View code on "
    [:a {:href "http://github.com/SagaxTech/gomoku"} "Github"]]])

(rum/mount (menu) (js/document.getElementById "app"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
