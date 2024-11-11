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
     * Complete the 'biggerIsGreater' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING w as parameter.
     */

    /*
     * Idea: find the pivot character from the right, 
     * for ex: abcd, the pivot will be c, c < d. we will start from the right because we want a new string that will be greater than the original one, 
     * so we're trying to keep the all characters before the pivot
     * from the pivot index to the right, we would choose the minimum character, greater than the pivot but need to be smallest.
     * for ex: after c is only d, so choose d, then swap d with c ==> abdc
     * then we need to sort the subset after the pivot
     * for ex: ecdigf => (1) pivot is d, after d the character f is greater but also smallest, swap d with f
     * => ecfigd => sort subset after pivot => ecfdgi
    */
    public static String biggerIsGreater(String w) {
        if (w.length() <= 1) return "no answer"; 

        char[] input = w.toCharArray();
        int pivotIdx = -1;
        // Find the pivot
        for (int i = input.length - 2; i >= 0; i--) {
            if (input[i] < input[i + 1]) {
                pivotIdx = i;
                break;
            }
        }

        // swap pivot with minimum char in the subset after
        if (pivotIdx < 0) {
            return "no answer";
        }

        //sort the subset
        Arrays.sort(input, pivotIdx + 1, input.length);

        int smallestIdx = -1;
        for (int i = pivotIdx + 1; i < input.length; i++) {
            if (input[i] > input[pivotIdx]) {
                smallestIdx = i;
                break;
            }
        }

        char temp = input[pivotIdx];
        input[pivotIdx] = input[smallestIdx];
        input[smallestIdx] = temp;

        //sort the subset
        Arrays.sort(input, pivotIdx + 1, input.length);

        return String.valueOf(input).compareTo(w) > 0 ? String.valueOf(input) : "no answer";
    }

}

public class BiggerIsGreater {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/quandoan/Documents/m2_java_projects/hackerrank/output.txt"));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, T).forEach(TItr -> {
            try {
                String w = bufferedReader.readLine();

                String result = Solution.biggerIsGreater(w);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
