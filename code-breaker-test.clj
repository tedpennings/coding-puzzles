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

(deftest exact-matches
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
  (testing "An empty list is returned when there are no exact matches"
    (let [colors1 [:red :orange :yellow :green]
          colors2 [:blue :violet :pink :chartreuse]]
      (is (= 0 (first (evaluate-guess colors1 colors2)))))))

(comment "TODO test inexact matches")

(run-tests)
