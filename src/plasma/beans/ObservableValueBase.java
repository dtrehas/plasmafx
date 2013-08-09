package plasma.beans;

public class ObservableValueBase<T> implements ObservableValue<T> {

	private final ValueAdapter<T> adapter;

	private transient final ObservableValueSupport<T> observableSupport;

	protected ObservableValueBase(final T value) {
		this(new ValueAdapter<T>() {
			private T _value = value;

			@Override
			public T get() {
				return _value;
			}

			@Override
			public void set(T value) {
				this._value = value;
			}
			
			@Override
			public String toString() {
				return "ValueAdapter [value=" + _value + "]";
			}
		});
	}

	protected ObservableValueBase(ValueAdapter<T> adapter) {
		this.adapter = adapter;
		this.observableSupport = new ObservableValueSupport<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public ObservableValue<T> getObservable() {
				return ObservableValueBase.this;
			}
		};
	}

	@Override
	public void addListener(InvalidationListener listener) {
		observableSupport.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		observableSupport.removeListener(listener);
	}

	@Override
	public void addListener(ChangeListener<T> listener) {
		observableSupport.addListener(listener);
	}

	@Override
	public void removeListener(ChangeListener<T> listener) {
		observableSupport.removeListener(listener);
	}

	@Override
	public T getValue() {
		return adapter.get();
	}

	protected void setValue(T value) {
		if (!isValueEqualTo(value)) {
			T oldValue = adapter.get();
			adapter.set(value);

			// notify descendants
			invalidated();

			// notify observers
			notifyListeners();

			// notify change listsners
			notifyChangeListeners(oldValue, value);
		}
	}

	protected void invalidated() {
		// nothing to do here
	}

	protected void notifyListeners() {
		observableSupport.notifyListeners();
	}

	protected void notifyChangeListeners(T oldValue, T newValue) {
		observableSupport.notifyListeners(oldValue, newValue);
	}

	protected final boolean isValueEqualTo(T value) {
		T currentValue = adapter.get();
		if (currentValue == null) {
			if (value != null) {
				return false;
			}
		} else if (!currentValue.equals(value)) {
			return false;
		}
		return true;
	}

	@Override
	public final String toString() {
		return super.toString() + " [value=" + adapter + "]";
	}

}
