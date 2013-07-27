(ns three
	(:require [clojure.math.numeric-tower :as math]))

; run with
; $ java -cp /Users/ted/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar:/Users/ted/.m2/repository/org/clojure/math.numeric-tower/0.0.2/math.numeric-tower-0.0.2.jar clojure.main is-power-of-three.clj 

; Performance
; real	0m7.096s
; user	0m8.074s
; sys	0m0.348s

(defn divides-by-3? [x]
	(= 0 (mod x 3)))

(defn power-of-3? [candidate]
	(if (< 0 candidate) (Exception.))
	(if (= 0 candidate)
		true
		(if-not (divides-by-3? candidate)
			false
			(if (= candidate 3)
				true
				(power-of-3? (/ candidate 3))))))


(doseq [x (filter power-of-3? (range 1000000))]
	(println x "is a power of three"))