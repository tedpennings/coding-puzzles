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

(defn find-inexact-matches [guess answer exact-matches]
  "Returns count of the inexact matches -- color and position -- between both sets"
  (comment "TODO implement inexact matches and remove stubbed -1 answer")
  -1)

(defn evaluate-guess [guess answer]
  "Evaluates a guess given an answer"
  (map validate-color-seq [guess answer])
  (let [exact-matches 
        (find-exact-matches guess answer)]
    (let [inexact-matches
        (find-inexact-matches guess answer exact-matches)]
      [(count exact-matches) inexact-matches])))

