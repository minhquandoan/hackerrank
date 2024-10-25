import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Solution {

  /*
   * Complete the 'superReducedString' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts STRING s as parameter.
   */

  /*
   * Idea: aaabccddd
   * looping each char, count the number of appearance for each
   * if a char has duplicate, need 2 char to remove
   * aaaa -> count = 4 -> remove all (count / 2 = 2 pairs)
   */

  public static String superReducedString(String s) {
    Function<String, String> reduceString = new Function<String, String>() {
      @Override
      public String apply(String t) {
        char[] input = t.toCharArray();
        int currCount = 0;
        int left = 0, right = 0;
        StringBuilder result = new StringBuilder();

        while (left < input.length) {
          while (right < input.length && input[right] == input[left]) {
            currCount++;
            right++;
          }

          if (currCount % 2 != 0) {
            result.append(input[left]);
          }

          left = right;
          currCount = 0;
        }

        return result.toString().isEmpty() ? "Empty String" : result.toString();
      }

    };

    while (checkReducedString(s)) {
      s = reduceString.apply(s);
    }

    return s;
  }

  /*
   * use 2 pointers to protect if the string can still be reduced
   */
  public static boolean checkReducedString(String s) {
    char[] input = s.toCharArray();
    int left = 0, right = 0;
    int currCount = 0;

    while (left < input.length) {
      while (right < input.length && input[right] == input[left]) {
        currCount++;
        right++;
      }

      if (currCount > 1) {
        return true;
      }

      left = right;
      currCount = 0;
    }
    return false;
  }

}

public class SuperReducedString {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String s = bufferedReader.readLine();

    String result = Solution.superReducedString(s);

    bufferedWriter.write(result);
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}
