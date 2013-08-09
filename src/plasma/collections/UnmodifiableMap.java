package plasma.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Unmodifiable implementation of the {@link Map} interface.
 */
public class UnmodifiableMap<K, V> implements Map<K, V> {
	private final Map<K, V> map;

	public UnmodifiableMap(Map<K, V> map) {
		if (map == null) {
			throw new IllegalArgumentException("map is null.");
		}

		this.map = map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet(); // TODO what about iterator
	}

	@Override
	public Collection<V> values() {
		return map.values(); // TODO what about iterator
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet(); // TODO what about iterator
	}

}
