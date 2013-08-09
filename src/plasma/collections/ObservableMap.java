package plasma.collections;

import java.util.Map;

import plasma.beans.Observable;

/**
 * A {@code Map} that notifies listeners of changes.
 */
public interface ObservableMap<K, V> extends Map<K, V>, Observable {

	/**
	 * Adds a listener to this observable map.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	void addObservableMapListener(ObservableMapListener<K, V> listener);

	/**
	 * Removes a listener from this observable map.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	void removeObservableMapListener(ObservableMapListener<K, V> listener);

}
