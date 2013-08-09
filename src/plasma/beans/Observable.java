package plasma.beans;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Observable {

	void addListener(InvalidationListener listener);

	void removeListener(InvalidationListener listener);

	public static abstract class ObservableSupport {
		
		private List<InvalidationListener> listeners;

		public abstract <T extends Observable> T getObservable();

		public void notifyListeners() {
			if (listeners != null) {
				for (InvalidationListener l : listeners) {
					l.invalidated(getObservable());
				}
			}
		}

		public void addListener(InvalidationListener listener) {
			if (listeners == null) {
				listeners = new CopyOnWriteArrayList<InvalidationListener>();
			}
			listeners.add(listener);
		}

		public void removeListener(InvalidationListener listener) {
			if (listeners != null) {
				listeners.remove(listener);
			}
		}
		
	}
}
