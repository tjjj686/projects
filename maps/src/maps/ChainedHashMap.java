
package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1;
    private static int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static int DEFAULT_INITIAL_CHAIN_CAPACITY = 5;
    private static int capacity;
    private static double threshold;
    private static int arrayLength;
    private static int num;
    /*
    Warning:
    DO NOT rename this `chains` field or change its type.
    We will be inspecting it in our Gradescope-only tests.

    An explanation of this field:
    - `chains` is the main array where you're going to store all of your data (see the [] square bracket notation)
    - The other part of the type is the AbstractIterableMap<K, V> -- this is saying that `chains` will be an
    array that can store an AbstractIterableMap<K, V> object at each index.
       - AbstractIterableMap represents an abstract/generalized Map. The ArrayMap you wrote in the earlier part
       of this project qualifies as one, as it extends the AbstractIterableMap class.  This means you can
       and should be creating ArrayMap objects to go inside your `chains` array as necessary. See the instructions on
       the website for diagrams and more details.
        (To jump to its details, middle-click or control/command-click on AbstractIterableMap below)
     */
    AbstractIterableMap<K, V>[] chains;


    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.threshold = resizingLoadFactorThreshold;
        this.capacity = initialChainCount;
        this.arrayLength = chainInitialCapacity;
        this.chains = this.createArrayOfChains(capacity);
        this.num = 0;
        for (int i = 0; i < chains.length; i++) {
            chains[i] = createChain(arrayLength);
        }
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    private int getIndex(Object key) {
        if (key == null) {
            return 0;
        } else {
            return Math.abs(key.hashCode()) % capacity;
        }
    }

    @Override
    public V get(Object key) {
        return chains[getIndex(key)].get(key);
    }

    public V puthelper(K key, V value, int i) {
        V re = null;
        if (chains[i].containsKey((key))) {
            re = chains[i].put(key, value);
        } else {
            re = chains[i].put(key, value);
            num++;
        }
        return re;
    }

    private void resize() {
        capacity *= 2;
        AbstractIterableMap<K, V>[] myMap = createArrayOfChains((capacity));
        for (int i = 0; i < capacity; i++) {
            myMap[i] = createChain(arrayLength);
        }
        for (AbstractIterableMap<K, V> cur : chains) {
            for (Entry<K, V> pair : cur) {
                myMap[getIndex(getIndex(pair.getKey()))].put(pair.getKey(), pair.getValue());
            }
        }
        chains = myMap;

    }

    public V put(K key, V value) {
        V re;
        if (num / capacity >= threshold) {
            resize();
        }
        if (num / capacity < threshold) {
            return puthelper(key, value, getIndex(key));
        }
        return null;

    }

    @Override
    public V remove(Object key) {
        V re = null;
        int i = 0;
        if (key == null) {
            i = 0;
        } else {
            i = Math.abs(key.hashCode()) % chains.length;
        }
        re = chains[i].remove(key);
        if (re != null) {
            num--;
        }
        return re;
    }

    @Override
    public void clear() {
        chains = createArrayOfChains(chains.length);
        for (int i = 0; i < chains.length; i++) {
            chains[i] = createChain(DEFAULT_INITIAL_CHAIN_CAPACITY);
        }
        num = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (AbstractIterableMap<K, V> item : chains) {
            if (item != null && item.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return num;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private int i = 0;
        private int rest;
        private Iterator<Map.Entry<K, V>> currentChain;
        // You may add more fields and constructor parameters

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.rest = num;

            currentChain = chains[i].iterator();
        }

        @Override
        public boolean hasNext() {
            return rest > 0 && i < chains.length;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (currentChain.hasNext()) {
                rest--;
                return currentChain.next();
            } else {
                this.currentChain = chains[++i].iterator();
                return next();
            }

        }
    }
}
