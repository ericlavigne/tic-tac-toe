(ns clojure.math.combinatorics)

;;; Copied portion of clojure.math.combinators that I'm using
;;; because the official library doesn't support ClojureScript.
;;; Would be nice if they just changed their .clj file to .cljc
;;; but code comments suggest they're trying to support Clojure
;;; 1.2 and a .cljc file requires Clojure 1.7+. Maybe they'd
;;; agree that ClojureScript is more important than old Clojure?

(defn cartesian-product
  "All the ways to take one item from each sequence"
  [& seqs]
  (let [v-original-seqs (vec seqs)
        step
        (fn step [v-seqs]
          (let [increment
                (fn [v-seqs]
                  (loop [i (dec (count v-seqs)), v-seqs v-seqs]
                    (if (= i -1) nil
                      (if-let [rst (next (v-seqs i))]
                        (assoc v-seqs i rst)
                        (recur (dec i) (assoc v-seqs i (v-original-seqs i)))))))]
            (when v-seqs
              (cons (map first v-seqs)
                    (lazy-seq (step (increment v-seqs)))))))]
    (when (every? seq seqs)
      (lazy-seq (step v-original-seqs)))))

(defn selections
  "All the ways of taking n (possibly the same) elements from the sequence of items"
  [items n]
  (apply cartesian-product (take n (repeat items))))
