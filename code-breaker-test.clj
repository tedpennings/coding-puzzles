(ns code-breaker.test
    (:use [clojure.test]
          [code-breaker]))

; run tests as follows:
; $ ./run-clj.sh code-breaker-test.clj 

(deftest input-validation
  (comment "This test could be refactored to use a macro for the test assertions")
  (testing "A nil parameter is not accepted"
    (is (thrown? IllegalArgumentException 
      (validate-color-seq nil))))
  (testing "An empty collection is not accepted"
    (is (thrown? IllegalArgumentException 
      (validate-color-seq empty))))
  (testing "A collection that is too small is not accepted"
    (is (thrown? IllegalArgumentException 
      (validate-color-seq [:red :green :blue]))))
  (testing "A sparse collection is not accepted"
    (is (thrown? IllegalArgumentException 
      (validate-color-seq [:red :green nil :blue]))))
  (testing "A collection that is too large is not accepted"
    (is (thrown? IllegalArgumentException 
      (validate-color-seq [:red :green :blue :chartreuse :yellow]))))
  (testing "A collection with invalid elements is not accepted"
    (is (thrown? IllegalArgumentException 
      (validate-color-seq [:my :own :color :scheme]))))
  (testing "A collection with invalid elements has the invalid elements listed in exception"
    (is (thrown-with-msg? IllegalArgumentException #"fake"
      (validate-color-seq [:red :green :blue :fake]))))
  (testing "A valid collection is accepted"
    (comment "This test passes by no exception being thrown")
      (validate-color-seq [:red :green :blue :chartreuse])))

(deftest game-internals-find-exact-matches
  (comment "TODO consider if this should be here... find-exact-matches should probably be private")
  (testing "A perfect set of exact matches can be identified"
    (let [colors [:yellow :green :blue :violet]]
      (is (= (find-exact-matches colors colors)
              [0 1 2 3]))))
  (testing "A set of half exact matches can be identified"
    (let [colors1 [:yellow :green :blue :orange]
          colors2 [:yellow :green :chartreuse :violet]]
      (is (= (find-exact-matches colors1 colors2) [0 1]))))
  (testing "A single exact matches can be identified"
    (let [colors1 [:red :orange :pink :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= (find-exact-matches colors1 colors2) [2]))))
  (testing "An empty list is returned when there are no exact matches"
    (let [colors1 [:red :orange :yellow :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= (find-exact-matches colors1 colors2) [])))))

(deftest game-play-exact
  (testing "A perfect set of exact matches can be identified"
    (let [colors [:yellow :green :blue :violet]]
      (is (= 4 (first (evaluate-guess colors colors))))))
  (testing "A set of half exact matches can be identified"
    (let [colors1 [:yellow :green :blue :orange]
          colors2 [:yellow :green :chartreuse :violet]]
      (is (= 2 (first (evaluate-guess colors1 colors2))))))
  (testing "A single exact matches can be identified"
    (let [colors1 [:red :orange :pink :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= 1 (first (evaluate-guess colors1 colors2))))))
  (testing "Zero is returned when there are no exact matches"
    (let [colors1 [:red :orange :yellow :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= 0 (first (evaluate-guess colors1 colors2)))))))

(deftest game-play-inexact
  (testing "A full set of inexact matches can be identified"
    (let [colors [:blue :violet :yellow :green]]
      (is (= 4 (second (evaluate-guess colors (reverse colors)))))))
  (testing "A set of half inexact matches can be identified"
    (let [colors1 [:yellow :green :blue :orange]
          colors2 [:chartreuse :violet :yellow :green]]
      (is (= 2 (second (evaluate-guess colors1 colors2))))))
  (testing "A single inexact matches can be identified"
    (let [colors1 [:red :orange :green :pink]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= 1 (second (evaluate-guess colors1 colors2))))))
  (testing "Two inexact matches for the same color only report as a single inexact match per game rules"
    (let [colors1 [:red :red :yellow :green]
          colors2 [:blue :violet :red :red]]
      (is (= 1 (second (evaluate-guess colors1 colors2))))))
  (testing "Zero is returned when there are no inexact matches"
    (let [colors1 [:red :orange :yellow :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= 0 (second (evaluate-guess colors1 colors2)))))))

(deftest game-play-both-exact-and-inexact
  (testing "A full set of exact matches can be identified and also returns zero inexact matches"
    (let [colors [:blue :violet :yellow :green]]
      (is (= [4 0] (evaluate-guess colors colors)))))
  (testing "A full set of inexact matches can be identified  and also returns zero exact matches"
    (let [colors [:blue :violet :yellow :green]]
      (is (= [0 4] (evaluate-guess colors (reverse colors))))))
  (testing "A set of half inexact matches can be identified and includes zero exact matches"
    (let [colors1 [:yellow :green :blue :orange]
          colors2 [:chartreuse :violet :yellow :green]]
      (is (= [0 2] (evaluate-guess colors1 colors2)))))
  (testing "A single inexact matches can be identified and includes zero exact matches"
    (let [colors1 [:red :orange :green :pink]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= [0 1] (evaluate-guess colors1 colors2)))))
  (testing "An exact match and an inexact match can occur for the same color"
    (let [colors1 [:blue :chartreuse :blue :pink]
          colors2 [:blue :violet :yellow :blue]]
      (is (= [1 1] (evaluate-guess colors1 colors2)))))
  (testing "Zero is returned twice when there are no exact or inexact matches"
    (let [colors1 [:red :orange :yellow :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= [0 0] (evaluate-guess colors1 colors2))))))

(run-tests)
