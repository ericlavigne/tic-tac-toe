(ns ui.clock)

(defn now []
  (js/Date.))

(def clock
  (atom (now)))

(defn tick []
  (reset! clock (now))
  (js/setTimeout tick 50))
(tick)

(defn seconds-passed
  "Determines seconds elapsed between two js/Date values"
  [time1 time2]
  (let [millis-elapsed (- (.getTime time2)
                          (.getTime time1))]
    (/ millis-elapsed 1000.0)))
