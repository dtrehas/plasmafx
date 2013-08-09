package plasma.collections;

public interface ObservableMapListener<K, V> {

	/**
	 * Notification that the value of an existing key has changed.
	 * 
	 * @param map
	 *            the {@code ObservableMap} that changed
	 * @param key
	 *            the key
	 * @param lastValue
	 *            the previous value
	 */
	public void mapKeyValueChanged(ObservableMap<K, V> map, K key, V lastValue);

	/**
	 * Notification that a key has been added.
	 * 
	 * @param map
	 *            the {@code ObservableMap} that changed
	 * @param key
	 *            the key
	 */
	public void mapKeyAdded(ObservableMap<K, V> map, K key);

	/**
	 * Notification that a key has been removed
	 * 
	 * @param map
	 *            the {@code ObservableMap} that changed
	 * @param key
	 *            the key
	 * @param value
	 *            value for key before key was removed
	 */
	public void mapKeyRemoved(ObservableMap<K, V> map, K key, V value);

}
