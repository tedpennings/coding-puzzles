(ns ^{:doc "Implementation of the Code Breaker game.
            See http://onestepback.org/vital_testing/ for game rules"
      :author "Ted Pennings"}
  code-breaker
  (:require clojure.set))

(def colors 
  "The valid colors for the game, as Clojure keywords"
  (set [:red :orange :yellow :green :blue :violet :pink :chartreuse]))

(defn validate-color-seq [candidate-colors]
  (if-not (coll? candidate-colors) (throw (IllegalArgumentException. "Expected input type assignable to collection")))
  (if-not (= 4 (count candidate-colors)) (throw (IllegalArgumentException. "Expected collection of four elements")))
  (let [candidate-color-set (set candidate-colors)]
    (if-not (clojure.set/subset? candidate-color-set colors)
      (throw (IllegalArgumentException. 
        (str 
          "Sequence contains invalid elements -> " 
          (apply str 
            (clojure.set/difference candidate-color-set colors))))))))

(defn drop-elements-by-indexes [haystack indexes]
  "Returns a copy of haystack without the values at the indexes in the elements array"
  (keep-indexed
    (fn [index item]
      (if 
        (some #{index} indexes)
        nil
        item))
    haystack))


(defn find-exact-matches [guess answer]
  "Returns positions of the exact matches -- color and position -- between guess and answer.
   Note: This is not the final answer for exact matches."
  (loop [i 3 matches []]
    (if (>= i 0)
      (recur 
        (dec i) 
        (if 
          (= (nth guess i) (nth answer i))
          (cons i matches)
          matches))
      matches)))

(defn count-inexact-matches [guess answer exact-matches]
  "Returns count of the inexact matches -- color and position -- between both sets"
  ; This works because the number of inexact matches is just a set theory problem.
  ; Specifically, counting them is just the intersection of the guess and answer,
  ; after exact matches are removed. The same approach is much more code in Java 
  ; because java.util.Set doesn't have set-theory methods like clojure.set.
  (let [guess-without-exact-matches (drop-elements-by-indexes guess exact-matches)]
    (count (clojure.set/intersection 
      (set guess-without-exact-matches) 
      (set answer)))))

(defn evaluate-guess [guess answer]
  "Evaluates a guess given an answer"
  (map validate-color-seq [guess answer])
  (let [exact-matches 
        (find-exact-matches guess answer)]
    [ (count exact-matches) 
      (count-inexact-matches guess answer exact-matches) ]))

