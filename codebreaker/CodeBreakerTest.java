package codebreaker;

import static codebreaker.CodeBreakerColor.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CodeBreakerTest {  

  @Test
  public void buildIsSane() {
    assertTrue(true);
  }

  @Test
  public void theCodeBreakerReturnsTheNumberOfExactPositionColorsForTwoMatches() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, YELLOW, CHARTREUSE, BLUE);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> firstTwoMatch = asList(RED, YELLOW, VIOLET, GREEN);

    // When
    int[] result = breaker.evaluateGuess(firstTwoMatch);

    // Then
    assertExactMatches(2, result);
    assertWrongPositionMatches(0, result);
  }

  @Test
  public void theCodeBreakerReturnsTheNumberOfExactPositionColorsForAllFourMatches() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, YELLOW, CHARTREUSE, BLUE);
    CodeBreaker breaker = new CodeBreaker(actualColors);

    // When
    int[] result = breaker.evaluateGuess(actualColors);

    // Then
    assertExactMatches(4, result);
    assertWrongPositionMatches(0, result);
  }

  @Test
  public void theCodeBreakerReturnsTheNumberOfCorrectColorsInWrongPositionForOneMatch() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, YELLOW, CHARTREUSE, BLUE);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> oneInexactMatch = asList(VIOLET, GREEN, RED, ORANGE);

    // When
    int[] result = breaker.evaluateGuess(oneInexactMatch);

    // Then
    assertExactMatches(0, result);
    assertWrongPositionMatches(1, result);
  }

  @Test
  public void theCodeBreakerReturnsTheNumberOfCorrectColorsInWrongPositionForThreeMatches() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, YELLOW, CHARTREUSE, BLUE);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> threeInexactMatches = asList(CHARTREUSE, GREEN, RED, YELLOW);

    // When
    int[] result = breaker.evaluateGuess(threeInexactMatches);

    // Then
    assertExactMatches(0, result);
    assertWrongPositionMatches(3, result);
  }

  @Test
  public void whenThereAreZeroMatches_TheCodeBreakerReportsZeroMatches() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, ORANGE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> zeroMatches = asList(BLUE, VIOLET, PINK, CHARTREUSE);

    // When
    int[] result = breaker.evaluateGuess(zeroMatches);

    // Then
    assertExactMatches(0, result);
    assertWrongPositionMatches(0, result);
  }

  @Test
  public void whenAllTheSameColorsAreGuessed_AndThereIsOneExactMatchOnly_OnlyOneExactMatchIsReported() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, BLUE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> onlyOneExactMatch = asList(BLUE,BLUE,BLUE,BLUE);

    // When
    int[] result = breaker.evaluateGuess(onlyOneExactMatch);

    // Then
    assertExactMatches(1, result);
    assertWrongPositionMatches(0, result);
  }

  @Test
  public void whenAnExactMatchIsPresentAndAnInexactMatchIsPresent_OnlyOneExactAndOneInexactAreReturned() {
    // Given
    List<CodeBreakerColor> actualColors = asList(RED, BLUE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> oneMatchOfEach = asList(ORANGE, BLUE, GREEN, CHARTREUSE);

    // When
    int[] result = breaker.evaluateGuess(oneMatchOfEach);

    // Then
    assertExactMatches(1, result);
    assertWrongPositionMatches(1, result);
  }

  @Test
  public void whenOneExactMatchAndOneInexactMatchOfTheSameColorArePresent_OneExactAndOneInexactMatchAreReported() {
    // Given
    List<CodeBreakerColor> actualColors = asList(CHARTREUSE, BLUE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    List<CodeBreakerColor> oneMatchOfEachSameColor = asList(ORANGE, BLUE, ORANGE, CHARTREUSE);

    // When
    int[] result = breaker.evaluateGuess(oneMatchOfEachSameColor);

    // Then
    assertExactMatches(1, result);
    assertWrongPositionMatches(1, result);    
  }
  @Test(expected = IllegalArgumentException.class)
  public void theCodeBreakerConstructorDoesNotAcceptANullListOfColors() {
    new CodeBreaker(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void theCodeBreakerConstructorDoesNotAcceptAListWithTooFewColors() {
    new CodeBreaker(asList(RED, YELLOW));
  }

  @Test(expected = IllegalArgumentException.class)
  public void theCodeBreakerConstructorDoesNotAcceptAListWithTooManyColors() {
    new CodeBreaker(asList(RED, YELLOW, CHARTREUSE, BLUE, VIOLET));
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaulateGuessDoesNotAcceptANullListOfColors() {
    List<CodeBreakerColor> actualColors = asList(CHARTREUSE, BLUE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    breaker.evaluateGuess(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaulateGuessDoesNotAcceptAListWithTooFewColors() {
    List<CodeBreakerColor> actualColors = asList(CHARTREUSE, BLUE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    breaker.evaluateGuess(asList(RED,BLUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void tevaulateGuessDoesNotAcceptAListWithTooManyColors() {
    List<CodeBreakerColor> actualColors = asList(CHARTREUSE, BLUE, YELLOW, GREEN);
    CodeBreaker breaker = new CodeBreaker(actualColors);
    breaker.evaluateGuess(asList(RED,GREEN,BLUE,ORANGE,CHARTREUSE));
  }


  private void assertExactMatches(int countToAssert, int[] result) {
    assertThat("Incorrect number of exact matches", result[0], is(equalTo(countToAssert)));
  }
  private void assertWrongPositionMatches(int countToAssert, int[] result) {
    assertThat("Incorrect number of wrong position matches", result[1], is(equalTo(countToAssert)));    
  }

}