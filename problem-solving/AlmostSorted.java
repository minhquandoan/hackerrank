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
import static java.util.AbstractMap.SimpleEntry;

class Result {
    static int diffCount;
    static List<Integer> sortedArr;

    /*
     * Complete the 'almostSorted' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    /*
     * Idea:
     * Appoach with sorting the array
     * Now we have 2 array: original arr and sorted one
     * compare the changed index between those 2
     *      for swap case: we need to find 2 peeks, the left most index that is changed and the right most one
     *                     even if the arr could not be sorted with swapping operations, still need to find 2 peeks to check if it can be reverse or not
     *                     Init diffCount to count the number changes of index,
     *                     if diffCount = 2, this array could be swapped
     *      for reverse case: if diffCount > 2, this array is possible to be reversed.
     *                        leverage 2 peeks from the swap operation, check each element from peek1 -> peek2 array with peek2 -> peek1 in sor
     */

    public static void almostSorted(List<Integer> arr) {
        var result = canSwap(arr);
        if (result.getKey()) {
            System.out.println("yes");
            System.out.println(
                    String.format("swap %d %d", result.getValue().getKey() + 1, result.getValue().getValue() + 1));
            return;
        }

        boolean result2 = canReverse(arr, result.getValue().getKey(), result.getValue().getValue());
        if (result2) {
            System.out.println("yes");
            System.out.println(
                    String.format("reverse %d %d", result.getValue().getKey() + 1, result.getValue().getValue() + 1));
            return;
        }
        // var result2 = canReverse(arr);
        // if (result2.getKey()) {

        // }

        System.out.println("no");
    }

    /*
     * 
     * 
     * public static AbstractMap.SimpleEntry<Boolean,
     * AbstractMap.SimpleEntry<Integer, Integer>> canReverse(List<Integer> arr) {
     * int left = 0, right = arr.size() - 1;
     * 
     * while (left < right && arr.get(left) < arr.get(right)) {
     * left++;
     * right--;
     * }
     * 
     * // Check mono
     * if (left >= right) return new AbstractMap.SimpleEntry<Boolean,
     * AbstractMap.SimpleEntry<Integer, Integer>>(
     * false, null
     * );
     * 
     * for (int i = left; i < right; i++) {
     * if (arr.get(i) < arr.get(i + 1)) return new AbstractMap.SimpleEntry<Boolean,
     * AbstractMap.SimpleEntry<Integer, Integer>>(
     * false, null
     * );
     * }
     * 
     * boolean result = arr.get(left) > arr.get(right);
     * if (left > 0) {
     * result = result && arr.get(right) > arr.get(left - 1);
     * }
     * 
     * if (right < arr.size() - 1) {
     * result = result && arr.get(left) < arr.get(right + 1);
     * }
     * 
     * return new AbstractMap.SimpleEntry<Boolean, AbstractMap.SimpleEntry<Integer,
     * Integer>>(
     * result,
     * result?new AbstractMap.SimpleEntry<Integer, Integer>(
     * left, right
     * ): null
     * );
     * }
     * 
     * public static AbstractMap.SimpleEntry<Boolean,
     * AbstractMap.SimpleEntry<Integer, Integer>> canSwap(List<Integer> array) {
     * List<Integer> arr = new ArrayList<>(array);
     * Deque<Integer> mono = new ArrayDeque<>();
     * mono.push(arr.get(0));
     * int swapCount = 0;
     * int peekIdx = -1;
     * int peekIdx2 = -1;
     * 
     * for (int i = 1; i < arr.size(); i++) {
     * if (arr.get(i) < mono.getLast()) {
     * if (swapCount >= 1) {
     * return new
     * AbstractMap.SimpleEntry<Boolean,AbstractMap.SimpleEntry<Integer,Integer>>(
     * false, null
     * );
     * }
     * // find second element to be swapped
     * peekIdx = i - 1;
     * int c = peekIdx > 0 ? arr.get(peekIdx - 1) : -1;
     * int d = arr.get(i);
     * 
     * int j = i;
     * while (j < arr.size() - 1 && arr.get(j) < mono.getLast()) {
     * // check valid
     * if (arr.get(j) > c && arr.get(j) < d) {
     * int a = j > 0 ? arr.get(j - 1) : -1;
     * int b = j < arr.size() - 1 ? arr.get(j + 1) : Integer.MAX_VALUE;
     * if (mono.getLast() > a && mono.getLast() < b) {
     * mono.removeLast();
     * mono.addLast(arr.get(j));
     * int temp = arr.get(peekIdx);
     * arr.set(peekIdx, arr.get(j));
     * arr.set(j, temp);
     * swapCount++;
     * }
     * }
     * j++;
     * }
     * 
     * if (swapCount < 1 && j == arr.size() - 1) {
     * mono.removeLast();
     * mono.addLast(arr.get(j));
     * int temp = arr.get(peekIdx);
     * arr.set(peekIdx, arr.get(j));
     * arr.set(j, temp);
     * swapCount++;
     * }
     * 
     * peekIdx2 = j;
     * }
     * mono.add(arr.get(i));
     * }
     * 
     * boolean result = (swapCount < 2 && (mono.size() == arr.size()));
     * return new AbstractMap.SimpleEntry<Boolean, AbstractMap.SimpleEntry<Integer,
     * Integer>>(
     * result,
     * new AbstractMap.SimpleEntry<Integer, Integer>(
     * peekIdx, peekIdx2
     * )
     * );
     * }
     */

    public static SimpleEntry<Boolean, SimpleEntry<Integer, Integer>> canSwap(List<Integer> arr) {
        sortedArr = new ArrayList<>(arr);
        Collections.sort(sortedArr);

        diffCount = 0;
        int start = -1, end = -1;

        for (int i = 0; i < arr.size(); i++) {
            if (sortedArr.get(i) != arr.get(i)) {
                if (start == -1)
                    start = i;
                else
                    end = i;
                diffCount++;
            }
        }

        return new SimpleEntry<Boolean, AbstractMap.SimpleEntry<Integer, Integer>>(
                diffCount == 2, new SimpleEntry<Integer, Integer>(
                        start, end));
    }

    public static boolean canReverse(List<Integer> arr, int a, int b) {
        for (int i = b, j = a; i >= a; i--, j++) {
            if (arr.get(i) != sortedArr.get(j))
                return false;
        }
        return true;
    }
}

public class AlmostSorted {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        Result.almostSorted(arr);

        bufferedReader.close();
    }
}
