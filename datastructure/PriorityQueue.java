package datastructure;

import java.util.AbstractQueue;
import java.util.Iterator;

public class PriorityQueue<E> extends AbstractQueue<E> {
    private Object[] array;
    private int DEFAULT_CAPACITY = 20;
    private int size;
    private QueueType type;

    public PriorityQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.type = QueueType.MAX_HEAP;
    }

    public PriorityQueue(QueueType type) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.type = type;
    }

    @Override
    public boolean add(E e) {
        // add element to the current index
        // TODO: Extend the underlying queue when reaching limitation
        array[size] = e;

        // heapify new element
        heapify(size);
        size++;
        return true;
    }

    public void heapify(int currIndex) {
        // check the current index and its parent index
        // change position if current index is greater(max-heap) or less (min-heap)
        siftUp(currIndex);
        return;
    }

    public void siftUp(int currIndex) {
        
        Comparable<? super E> key = (Comparable<? super E>) this.array[currIndex];

        while (currIndex > 0) {
            int parentIndex = (currIndex - 1) >>> 1; // division by 2 with shifting bit
            Object parent = this.array[parentIndex];
            if (key.compareTo((E) parent) <= 0) {
                break;
            }

            this.array[currIndex] = parent;
            currIndex = parentIndex;
        }

        this.array[currIndex] = key;
    }

    public void siftDown(int currIndex) {
        Object key = this.array[0];
        int k = currIndex;
        int half = this.size() >>> 1;

        while (k < half) {
            int child = (k << 1) + 1;
            int right = child + 1;
            Object c = this.array[k];

            if (right < this.size() && 
                ((Comparable<? super E>) key).compareTo((E) this.array[right]) < 0) {

                c = this.array[child = right];
            }

            if (((Comparable<? super E>) key).compareTo((E) this.array[child]) < 0) {
                c = this.array[child];
            }

            this.array[k] = c;
            k = child;
        }
    }

    @Override
    public boolean offer(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offer'");
    }

    @Override
    public E poll() {
        // remove the root element, replace it by the last element in the queue, delete the last element
        Object root = this.array[0];
        this.array[0] = this.array[--this.size];
        this.array[this.size] = null;

        siftDown(0);

        return (E) root;
    }

    @Override
    public E peek() {
        return size > 0 ? (E) this.array[0] : null;
    }

    @Deprecated
    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public int size() {
        return this.size;
    }
    
}
