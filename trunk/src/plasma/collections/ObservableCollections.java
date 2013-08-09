package plasma.collections;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import plasma.beans.InvalidationListener;
import plasma.beans.Observable;

/**
 * {@code ObservableCollections} provides factory methods for creating
 * observable lists and maps.
 */
public class ObservableCollections {

	/**
	 * Creates and returns an {@code ObservableList} wrapping the supplied
	 * {@code List}.
	 * 
	 * @param list
	 *            the {@code List} to wrap
	 * @return an {@code ObservableList}
	 * @throws IllegalArgumentException
	 *             if {@code list} is {@code null}
	 */
	public static <E> ObservableList<E> observableList(List<E> list) {
		if (list == null) {
			throw new IllegalArgumentException("List must be non-null");
		}
		return new ObservableListImpl<E>(list);
	}

	/**
	 * Creates and returns an {@code ObservableMap} wrapping the supplied
	 * {@code Map}.
	 * 
	 * @param map
	 *            the {@code Map} to wrap
	 * @return an {@code ObservableMap}
	 * @throws IllegalArgumentException
	 *             if {@code map} is {@code null}
	 */
	public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
		if (map == null) {
			throw new IllegalArgumentException("Map must be non-null");
		}
		return new ObservableMapImpl<K, V>(map);
	}

	// ---------------------------------------------------------------------
	// ObservableList implementation.
	// ---------------------------------------------------------------------

	private static final class ObservableListImpl<E> extends AbstractList<E>
			implements ObservableList<E> {

		private final List<E> list;
		private final List<ObservableListListener<E>> listeners;
		private final ObservableSupport observableSupport;

		ObservableListImpl(List<E> list) {
			this.list = list;
			this.listeners = new CopyOnWriteArrayList<ObservableListListener<E>>();
			this.observableSupport = new ObservableSupport() {
				@SuppressWarnings("unchecked")
				@Override
				public Observable getObservable() {
					return ObservableListImpl.this;
				}
			};
		}

		@Override
		public E get(int index) {
			return list.get(index);
		}

		@Override
		public int size() {
			return list.size();
		}

		@Override
		public E set(int index, E element) { // TODO check equals?
			E oldValue = list.set(index, element);
			observableSupport.notifyListeners();
			for (ObservableListListener<E> listener : listeners) {
				listener.listElementReplaced(this, index, oldValue);
			}
			return oldValue;
		}

		@Override
		public void add(int index, E element) {
			list.add(index, element);
			modCount++;
			observableSupport.notifyListeners();
			for (ObservableListListener<E> listener : listeners) {
				listener.listElementsInserted(this, index, 1);
			}
		}

		@Override
		public E remove(int index) {
			E oldValue = list.remove(index);
			modCount++;
			observableSupport.notifyListeners();
			for (ObservableListListener<E> listener : listeners) {
				listener.listElementsRemoved(this, index,
						Collections.singletonList(oldValue));
			}
			return oldValue;
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			return addAll(size(), c);
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			if (list.addAll(index, c)) {
				modCount++;
				observableSupport.notifyListeners();
				for (ObservableListListener<E> listener : listeners) {
					listener.listElementsInserted(this, index, c.size());
				}
			}
			return false;
		}

		@Override
		public void clear() {
			List<E> dup = new ArrayList<E>(list);

			list.clear();
			modCount++;
			observableSupport.notifyListeners();
			if (dup.size() != 0) {
				for (ObservableListListener<E> listener : listeners) {
					listener.listElementsRemoved(this, 0, dup);
				}
			}
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return list.containsAll(c);
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return list.toArray(a);
		}

		@Override
		public Object[] toArray() {
			return list.toArray();
		}

		@Override
		public void addObservableListListener(ObservableListListener<E> listener) {
			listeners.add(listener);
		}

		@Override
		public void removeObservableListListener(
				ObservableListListener<E> listener) {
			listeners.remove(listener);
		}

		@Override
		public void addListener(InvalidationListener listener) {
			observableSupport.addListener(listener);
		}

		@Override
		public void removeListener(InvalidationListener listener) {
			observableSupport.removeListener(listener);
		}

	}

	// ---------------------------------------------------------------------
	// ObservableMap implementation.
	// ---------------------------------------------------------------------

	private static final class ObservableMapImpl<K, V> extends
			AbstractMap<K, V> implements ObservableMap<K, V> {

		private final Map<K, V> map;
		private Set<Map.Entry<K, V>> entrySet;
		private final List<ObservableMapListener<K, V>> listeners;
		private final ObservableSupport observableSupport;

		ObservableMapImpl(Map<K, V> map) {
			this.map = map;
			this.listeners = new CopyOnWriteArrayList<ObservableMapListener<K, V>>();
			this.observableSupport = new ObservableSupport() {
				@SuppressWarnings("unchecked")
				@Override
				public Observable getObservable() {
					return ObservableMapImpl.this;
				}
			};
		}

		@Override
		public void clear() {
			// Remove all elements via iterator to trigger notification
			Iterator<K> iterator = keySet().iterator();
			while (iterator.hasNext()) {
				iterator.next();
				iterator.remove();
			}
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
		public Set<Map.Entry<K, V>> entrySet() {
			Set<Map.Entry<K, V>> es = entrySet;
			return es != null ? es : (entrySet = new EntrySet());
		}

		@Override
		public V get(Object key) {
			return map.get(key);
		}

		@Override
		public boolean isEmpty() {
			return map.isEmpty();
		}

		@Override
		public V put(K key, V value) {
			V lastValue;
			if (containsKey(key)) {
				lastValue = map.put(key, value);
				observableSupport.notifyListeners();
				for (ObservableMapListener<K, V> listener : listeners) {
					listener.mapKeyValueChanged(this, key, lastValue);
				}
			} else {
				lastValue = map.put(key, value);
				observableSupport.notifyListeners();
				for (ObservableMapListener<K, V> listener : listeners) {
					listener.mapKeyAdded(this, key);
				}
			}
			return lastValue;
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			for (K key : m.keySet()) {
				put(key, m.get(key));
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public V remove(Object key) {
			if (containsKey(key)) {
				V value = map.remove(key);
				observableSupport.notifyListeners();
				for (ObservableMapListener<K, V> listener : listeners) {
					listener.mapKeyRemoved(this, (K) key, value);
				}
				return value;
			}
			return null;
		}

		@Override
		public int size() {
			return map.size();
		}

		@Override
		public void addObservableMapListener(
				ObservableMapListener<K, V> listener) {
			listeners.add(listener);
		}

		@Override
		public void removeObservableMapListener(
				ObservableMapListener<K, V> listener) {
			listeners.remove(listener);
		}

		@Override
		public void addListener(InvalidationListener listener) {
			observableSupport.addListener(listener);
		}

		@Override
		public void removeListener(InvalidationListener listener) {
			observableSupport.removeListener(listener);
		}

		private class EntryIterator implements Iterator<Map.Entry<K, V>> {
			private Iterator<Map.Entry<K, V>> realIterator;
			private Map.Entry<K, V> last;

			EntryIterator() {
				realIterator = map.entrySet().iterator();
			}

			@Override
			public boolean hasNext() {
				return realIterator.hasNext();
			}

			@Override
			public Map.Entry<K, V> next() {
				last = realIterator.next();
				return last;
			}

			@Override
			public void remove() {
				if (last == null) {
					throw new IllegalStateException();
				}
				Object toRemove = last.getKey();
				last = null;
				ObservableMapImpl.this.remove(toRemove);
			}
		}

		private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
			@Override
			public Iterator<Map.Entry<K, V>> iterator() {
				return new EntryIterator();
			}

			@Override
			@SuppressWarnings("unchecked")
			public boolean contains(Object o) {
				if (!(o instanceof Map.Entry)) {
					return false;
				}
				Map.Entry<K, V> e = (Map.Entry<K, V>) o;
				return containsKey(e.getKey());
			}

			@Override
			@SuppressWarnings("unchecked")
			public boolean remove(Object o) {
				if (o instanceof Map.Entry) {
					K key = ((Map.Entry<K, V>) o).getKey();
					if (containsKey(key)) {
						remove(key);
						return true;
					}
				}
				return false;
			}

			@Override
			public int size() {
				return ObservableMapImpl.this.size();
			}

			@Override
			public void clear() {
				ObservableMapImpl.this.clear();
			}
		}
	}

}
