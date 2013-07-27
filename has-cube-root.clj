(ns cubes
	(:require [clojure.math.numeric-tower :as math]))

; run with
; $ java -cp /Users/ted/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar:/Users/ted/.m2/repository/org/clojure/math.numeric-tower/0.0.2/math.numeric-tower-0.0.2.jar clojure.main has-cube-root.clj

; Performance (time)
; real	0m15.282s
; user	0m16.061s
; sys	0m0.242s


(defn cube [i]
	(math/expt i 3))

(defn has-cube-root [x]
	(loop [ i 0 
		    sum (cube i) ]
		(if (= sum x) 
			true
			(if (< sum x)
				(recur (inc i) (cube (inc i)))
				false))))

(doseq [cube (filter has-cube-root (range 1000000))]
	(println cube "has cube root" (math/round (math/expt cube 1/3))))
	
		