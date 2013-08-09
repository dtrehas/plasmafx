package plasma.collections;

import java.util.List;

/**
 * List listener interface.
 */
public interface ObservableListListener<T> {

	/**
	 * Notification that elements have been inserted to the list.
	 * 
	 * @param list
	 *            the {@code ObservableList} that has changed
	 * @param index
	 *            the index the elements were inserted to
	 * @param length
	 *            the number of elements that were inserted
	 */
	public void listElementsInserted(ObservableList<T> list, int index,
			int length);

	/**
	 * Notification that elements have been removed from the list.
	 * 
	 * @param list
	 *            the {@code ObservableList} that has changed
	 * @param index
	 *            the starting index the elements were removed from
	 * @param oldElements
	 *            a list containing the elements that were removed.
	 */
	public void listElementsRemoved(ObservableList<T> list, int index,
			List<T> oldElements);

	/**
	 * Notification that an element has been replaced by another in the list.
	 * 
	 * @param list
	 *            the {@code ObservableList} that has changed
	 * @param index
	 *            the index of the element that was replaced
	 * @param oldElement
	 *            the element at the index before the change
	 */
	public void listElementReplaced(ObservableList<T> list, int index,
			T oldElement);

}
