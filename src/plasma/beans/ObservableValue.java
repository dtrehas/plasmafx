package plasma.beans;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ObservableValue<T> extends Observable {

	void addListener(ChangeListener<T> listener);

	void removeListener(ChangeListener<T> listener);

	T getValue();

	public static abstract class ObservableValueSupport<T> extends ObservableSupport  {
		
		private transient List<ChangeListener<T>> changeListeners;
		
		public void notifyListeners(T oldValue, T newValue) {
			if (changeListeners != null) {
				ObservableValue<T> observable = getObservable();
				for (ChangeListener<? super T> l : changeListeners) {
					l.changed(observable, oldValue, newValue);
				}
			}
		}

		public void addListener(ChangeListener<T> listener) {
			if (changeListeners == null) {
				changeListeners = new CopyOnWriteArrayList<ChangeListener<T>>();
			}
			changeListeners.add(listener);
		}

		public void removeListener(ChangeListener<T> listener) {
			if (changeListeners != null) {
				changeListeners.remove(listener);
			}
		}

	}
}
