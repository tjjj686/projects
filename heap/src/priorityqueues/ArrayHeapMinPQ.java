package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T extends Comparable<T>> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    public int size;
    List<PriorityNode<T>> items;
    private Map<T, Integer> myMap;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        this.size = 0;
        items.add(null);
        myMap = new HashMap<>();
    }


    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private int swap(int children, int parents) {
        PriorityNode<T> cur = items.get(parents);
        items.set(parents, items.get(children));  //replace parent node with children
        items.set(children, cur);                //replace childern with parent;
        return parents;
    }

    private boolean condSwap(int i) {
        return i / 2 > 0 && items.get(i).getPriority() < items.get(i / 2).getPriority();
    }

    @Override
    public void add(T item, double priority) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items.add(new PriorityNode<>(item, priority));
        size++;
        int i = size;
        while (condSwap(i)) {
            int before = i;
            i = swap(i, i / 2); //swap the children to parents.
            myMap.put(items.get(before).getItem(), before);
            myMap.put(items.get(i).getItem(), i);
        }
        myMap.put(item, i);
    }

    @Override
    public boolean contains(T item) {
        if (myMap.containsKey(item)) {
            return true;
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return (items.get(1).getItem());
    }

    private void sink(int parent, int child) {
        PriorityNode<T> cur = items.get(child);
        items.set(child, items.get(parent));
        items.set(parent, cur);
    }

    private boolean condSink1(int i) {
        if (size >= 2 * i + 1) {
            if ((size > 1 && i < size && items.get(i).getPriority() >= items.get(2 * i).getPriority() ||
                items.get(i).getPriority() > items.get(2 * i + 1).getPriority())) {
                return true;
            }
        }
        return false;
    }

    private int makeSink1(int i) {
        if (items.get(2 * i).getPriority() < items.get(2 * i + 1).getPriority()) {
            sink(i, 2 * i);
            return 2 * i;
        } else {
            sink(i, 2 * i + 1);
            return 2 * i + 1;
        }
    }

    private boolean condSink2(int i) {
        if (size >= 2 * i) {
            if ((size > 1 && items.get(i).getPriority() > items.get(2 * i).getPriority())) {
                return true;
            }
        }
        return false;
    }

    private int makeSink2(int i) {
        sink(i, 2 * i);
        return 2 * i;
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T re = items.get(1).getItem();
        if (size == 1) {
            myMap.remove(items.get(1).getItem());
            items.remove(1);
            size--;
        } else {
            myMap.remove(re);
            myMap.put(items.get(size).getItem(), 1);
            items.set(1, items.remove(size));
            size--;
            int i = 1;
            while (condSink1(i)) {
                int before = i;
                i = makeSink1(i);     //i: after sink
                myMap.put(items.get(before).getItem(), before);
                myMap.put(items.get(i).getItem(), i);
            }
            while (condSink2(i)) {
                int before = i;
                i = makeSink2(i);
                myMap.put(items.get(before).getItem(), before);
                myMap.put(items.get(i).getItem(), i);
            }
        }
        return re;
    }
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        if (contains((item))) {
            int i = myMap.get(item);
            items.get(i).setPriority(priority);
            while (condSink1(i)) {
                int before = i;
                i = makeSink1(i);
                myMap.put(items.get(before).getItem(), before);
                myMap.put(items.get(i).getItem(), i);
            }
            while (condSink2(i)) {
                int before = i;
                i = makeSink2(i);
                myMap.put(items.get(before).getItem(), before);
                myMap.put(items.get(i).getItem(), i);
            }
            while (condSwap(i)) {
                int before = i;
                i = swap(i, i / 2);
                myMap.put(items.get(before).getItem(), before);
                myMap.put(items.get(i).getItem(), i);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
}
