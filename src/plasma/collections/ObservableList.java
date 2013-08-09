package plasma.collections;

import java.util.List;

import plasma.beans.Observable;

/**
 * A {@code List} that notifies listeners of changes.
 */
public interface ObservableList<E> extends List<E>, Observable {

	/**
	 * Adds a listener that is notified when the list changes.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addObservableListListener(ObservableListListener<E> listener);

	/**
	 * Removes a listener.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeObservableListListener(ObservableListListener<E> listener);
	
}
