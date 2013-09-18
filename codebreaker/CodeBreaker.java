package codebreaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * See http://onestepback.org/vital_testing/ for details
 * @author Ted Pennings
 */
public class CodeBreaker {
  private final List<CodeBreakerColor> colors;

  public CodeBreaker(List<CodeBreakerColor> colors) {
    validateColors(colors);
    // defensive copy
    this.colors = Collections.unmodifiableList(new ArrayList<CodeBreakerColor>(colors));
  }

  public int[] evaluateGuess(List<CodeBreakerColor> guess) {
    validateColors(guess);
    boolean[] exactMatches = findExactMatches(guess);
    int wrongPositionMatches = countWrongPositionMatches(guess, exactMatches);
    return resultFor(countBitsOn(exactMatches), wrongPositionMatches);
  }

  private boolean[] findExactMatches(List<CodeBreakerColor> guess) {
    boolean[] exactMatches = new boolean[4];
    for (int i = 0; i < 4; i++) {
      CodeBreakerColor candidate = guess.get(i);
      CodeBreakerColor actual = colors.get(i);
      exactMatches[i] = candidate.equals(actual);
    }
    return exactMatches;
  }

  private int countWrongPositionMatches(List<CodeBreakerColor> guess, boolean[] exactMatches) {
    int otherMatches = 0;
    List<CodeBreakerColor> alreadyMatchedColors = new ArrayList<CodeBreakerColor>();
    for (int i = 0; i < 4; i++) {
      if (exactMatches[i]) {
        // there is an exact match at this color guess position
        // skip this color and do not double-count match per game rules
        continue;
      }
      CodeBreakerColor candidate = guess.get(i);
      if (alreadyMatchedColors.contains(candidate)) {
        // we have already reported that this color is correct (but in wrong position)
        // skip this color -- do not report twice that the color is present
        continue;
      }
      if (colors.contains(candidate)) {
        // yes, candidate is in the answer color set, but that's not sufficient.
        // in the case of a guess that contains one exact match of color X and
        // another incorrect guess of color X (answer only has one X), we must
        // not report that incorrect guess as part of the wrong position matches
        if (countAllInstancesOf(candidate, colors) > countExactMatchInstancesOf(candidate, guess, exactMatches)) {
          // it's a match, and there's another non-exact match we need to report
          otherMatches++;
          alreadyMatchedColors.add(candidate);
        }
      }
    }
    return otherMatches;
  }

  private int countAllInstancesOf(CodeBreakerColor color, List<CodeBreakerColor> haystack) {
    int instances = 0;
    for (CodeBreakerColor needle : haystack) {
      if (color.equals(needle)) {
        instances++;
      }
    }
    return instances;
  }

  private int countExactMatchInstancesOf(CodeBreakerColor color, List<CodeBreakerColor> haystack, boolean[] exactMatches) {
    int matches = 0;
    for (int i = 0; i < 4; i++) {
      if (exactMatches[i]) {
        CodeBreakerColor needle = haystack.get(i);
        if (needle.equals(color)) {
          matches++;
        }
      }
    }
    return matches;
  }

  private int countBitsOn(boolean[] bits) {
    int count = 0;
    for (boolean bit : bits) {
      if (bit) count++;
    }
    return count;
  }

  private void validateColors(List<CodeBreakerColor> colors) {
    if (colors == null || colors.size() != 4) {
      throw new IllegalArgumentException("Expected a list of four colors. Received: " + colors);
    }
    for (CodeBreakerColor color : colors) {
      if (color == null) {
        // this gets us out of null-checking everywhere in the code
        throw new IllegalArgumentException("Expected a list with all non-null entries. Received: " + colors);
      }
    }
  }

  private int[] resultFor(int rightColorRightPosition, int rightColorWrongPosition) {
    return new int[] { rightColorRightPosition, rightColorWrongPosition};
  }

}