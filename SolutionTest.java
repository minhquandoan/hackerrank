import datastructure.PriorityQueue;
import datastructure.QueueType;

public class SolutionTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(QueueType.MAX_HEAP);

        queue.add(4);
        queue.add(6);
        queue.add(5);
        queue.poll();

        // System.out.println(queue);
        // java.util.PriorityQueue<Integer> queue = new java.util.PriorityQueue<>();
        // queue.add(4);
        // queue.add(6);
        // queue.add(5);
        // queue.poll();
    }
}
