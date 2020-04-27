package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    int l;
    /*
    Warning:
    DO NOT rename this `entries` field or change its type.
    We will be inspecting it in our Gradescope-only tests.

    An explanation of this field:
    - `entries` is the array where you're going to store all of your data (see the [] square bracket notation)
    - The other part of the type is the SimpleEntry<K, V> -- this is saying that `entries` will be an
    array that can store a SimpleEntry<K, V> object at each index.
       - SimpleEntry represents an object containing a key and a value.
        (To jump to its details, middle-click or control/command-click on SimpleEntry below)

    */
    SimpleEntry<K, V>[] entries;
    // You may add extra fields or helper methods though!
    SimpleEntry<K, V>[] entries1;

    public void arrayMapt(int initialCapacity) {
        this.entries1 = this.createArrayOfEntries(initialCapacity);
    }

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.l = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (SimpleEntry<K, V> item : entries) {
            if (item != null && item.getKey() == null && key == null || item != null && item.getKey().equals(key)) {
                return item.getValue();
            }
        }
        return null;

    }

    @SuppressWarnings("checkstyle:WhitespaceAround")
    @Override
    public V put(K key, V value) {
        int i = 0;
        V re = null;
        for (SimpleEntry<K, V> item : entries) {
            if (item != null && item.getKey() == null && key == null || item != null && item.getKey().equals(key)) {
                re = item.getValue();
                item.setValue(value);
                i++;
                return re;
            }
        }
        if (i == 0) {
            for (SimpleEntry<K, V> item : entries) {
                if (item == null) {
                    entries[i] = new SimpleEntry<K, V>(key, value);
                    l++;
                    return null;
                }
                i++;
            }
        }
        if (i >= entries.length) {
            int j = 0;
            arrayMapt(2 * entries.length);
            for (SimpleEntry<K, V> item : entries) {
                entries1[j++] = item;
            }
            entries = entries1;
            entries[j] = new SimpleEntry<>(key, value);
            l++;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        V re = null;
        int i = 0;
        for (SimpleEntry<K, V> item : entries) {
            if (item != null && item.getKey() == null && key == null || item != null && item.getKey().equals(key)) {
                re = item.getValue();
                entries[i] = entries[size() - 1];
                entries[size() - 1] = null;
                l--;
                return re;
            }
            i++;
        }
        return re;
    }

    @Override
    public void clear() {
        entries = createArrayOfEntries(entries.length);
        l = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (SimpleEntry<K, V> item : entries) {
            if (item != null) {
                if (item.getKey() == null && key == null || item.getKey() != null && item.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return l;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ArrayMapIterator<>(this.entries);
    }


    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        int i = 0;
        // You may add more fields and constructor parameters
        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
        }

        @Override
        public boolean hasNext() {
            return entries[i] != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                return entries[i++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
