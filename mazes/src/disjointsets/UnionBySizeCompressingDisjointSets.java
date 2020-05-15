package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A quick-union-by-size data structure with path compression.
 *
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    Map<T, Integer> mymap = new HashMap<>();
    int index;
    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        this.index = 0;
        pointers = new ArrayList<>();
    }

    @Override
    public void makeSet(T item) {
        pointers.add(-1);
        mymap.put(item, index++);           //store information about item's index;
    }

    @Override
    public int findSet(T item) {
        if (!mymap.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        int findIndex = mymap.get(item);
        while (pointers.get(findIndex) >= 0) {
            findIndex = pointers.get(findIndex);
        }

        int findIndexs = mymap.get(item);
        while (pointers.get(findIndexs) >= 0) {
            pointers.set(findIndexs, findIndex);
            findIndexs = pointers.get(findIndexs);
        }


        return findIndex;
    }

    @Override
    public boolean union(T item1, T item2) {
        if (!mymap.containsKey(item1) || !mymap.containsKey(item2)) {
            throw new IllegalArgumentException();
        }
        int indexOne = findSet(item1);
        int indexTwo = findSet(item2);
        if (indexOne == indexTwo) {
            return false;
        } else {
            if (pointers.get(indexOne) < pointers.get(indexTwo)) {
                pointers.set(indexTwo, indexOne);
                int updateSize = pointers.get(indexOne) - 1;
                pointers.set(indexOne, updateSize);
            } else {
                pointers.set(indexOne, indexTwo);
                int updateSize = pointers.get(indexTwo) - 1;
                pointers.set(indexTwo, updateSize);
            }
            return true;
        }
    }
}
