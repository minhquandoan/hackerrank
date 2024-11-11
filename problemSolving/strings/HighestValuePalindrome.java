package problemSolving.strings;

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

class Result {

    /*
     * Complete the 'highestValuePalindrome' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. INTEGER n
     *  3. INTEGER k
     */

    /*
    Idea:
        https://medium.com/@lonell.liburd/highest-value-palindrome-1275edcd1430
    */
    public static String highestValuePalindrome(String s, int n, int k) {
        char[] result = s.toCharArray();
        int diff = 0;

        // check the differences that need to be changed
        for (int i = 0; i < n/2; i++) {
            if (result[i] != result[n - i - 1]) diff++;
        }

        if (diff > k) {
            return "-1";
        }

        if (k <= 0) {
            return isPalindrome(String.valueOf(result)) ? String.valueOf(result) : "-1";
        }

        int left = 0, right = n - 1;
        while (left <= right) {
            // equal and not equal to 9 for both
            if (result[left] == result[right]) {
                if (k > 1 && (k - 2) >= diff && result[left] != '9') {
                    result[right] = '9';
                    result[left] = '9';
                    k -= 2;
                }
            } else { // not equal
                if (k > 1 && (k-2) >= diff - 1) { // if k >= 2, diff - 1 =>  decrement the difference because we have just resolved a difference
                    if (result[left] != '9') {
                        result[left] = '9';
                        k--;
                    }

                    if (result[right] != '9') {
                        result[right] = '9';
                        k--;
                    }
                } else {
                    if (result[left] > result[right]) {
                        result[right] = result[left];
                    } else {
                        result[left] = result[right];
                    }
                    k--;
                }
                diff--;

            }

            if (left==right && k > 0) {
                result[left] = '9';
                k--;
            }

            left++;
            right--;
        }

        return isPalindrome(String.valueOf(result)) ? String.valueOf(result) : "-1";
    }

    public static boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

}

public class HighestValuePalindrome {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String s = bufferedReader.readLine();

        String result = Result.highestValuePalindrome(s, n, k);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
