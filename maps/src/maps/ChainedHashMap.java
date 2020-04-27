package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 0.5;
    private static int DEFAULT_INITIAL_CHAIN_COUNT = 1;
    private static int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;
    public double lf = DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD / DEFAULT_INITIAL_CHAIN_COUNT;
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
    AbstractIterableMap<K, V>[] chainst;


    // You're encouraged to add extra fields (and helper methods) though!
    public void chainedHashMapt(int initialChainCount) {
        this.chainst = this.createArrayOfChains(initialChainCount);
    }

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = resizingLoadFactorThreshold;
        this.DEFAULT_INITIAL_CHAIN_COUNT = initialChainCount;
        this.DEFAULT_INITIAL_CHAIN_CAPACITY = chainInitialCapacity;
        this.chains = this.createArrayOfChains(initialChainCount);
        this.num = 0;
        for (int i = 0; i < chains.length; i++) {
            chains[i] = createChain(chainInitialCapacity);
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

    @Override
    public V get(Object key) {
        for (AbstractIterableMap<K, V> item : chains) {
            if (item != null && item.containsKey(key)) {
                return item.get(key);
            }
        }
        return null;
    }

    public V puthelper(K key, V value, int i) {
        for (AbstractIterableMap<K, V> item : chains) {
            if (key == null && item == null) {
                chains[i] = new ArrayMap<>(DEFAULT_INITIAL_CHAIN_CAPACITY);
                chains[i].put(key, value);
                num++;
            }
            if (key != null && item == null && i == Math.abs(key.hashCode()) % chains.length) {
                chains[i] = new ArrayMap<>(DEFAULT_INITIAL_CHAIN_CAPACITY);
                chains[i].put(key, value);
                num++;
            }
            if (key != null && item != null && i == Math.abs(key.hashCode()) % chains.length) {
                chains[i].put(key, value);
                num++;
            }
            if (key == null && item != null) {
                chains[0].put(key, value);
                num++;
            }
            i++;
        }
        return null;
    }

    public V put(K key, V value) {
        int i = 0;
        V re;
        for (AbstractIterableMap<K, V> item : chains) {
            if (item != null && item.containsKey(key)) {
                re = item.get(key);
                item.put(key, value);
                i++;
                return re;
            }
        }
        if (i == 0 && size() / chains.length < DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD) {
            return puthelper(key, value, i);
        }

        if (size() / chains.length >= DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD - 1) {
            int x = 0;
            chainedHashMapt(2 * chains.length);

            for (int j = 0; j < chainst.length; j++) {
                chainst[j] = createChain(20);
            }

            for (AbstractIterableMap<K, V> bucket : chains) {
                for (Entry<K, V> pair : bucket) {
                    i = Math.abs(pair.getKey().hashCode()) % chainst.length;
                    chainst[i].put(pair.getKey(), pair.getValue());
                }
            }
            chains = chainst;
            i = 0;
            puthelper(key, value, i);
        }
        return null;

    }

    @Override
    public V remove(Object key) {
        V re = null;
        int i = Math.abs(key.hashCode()) % chains.length;
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
