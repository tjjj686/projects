package maps;

import java.util.Map;

public class ArrayMapTests extends BaseMapTests {
    @Override
    protected <K, V> Map<K, V> createMap() {
        return new ArrayMap<>();
    }
}
