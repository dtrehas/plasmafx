package plasma.beans;

public abstract class BindingSupport<T> {

	private final InvalidationListener listener = new InvalidationListener() {
		@Override
		public void invalidated(Observable observable) {
			invalidate();
		}
	};

	private ObservableValue<?> other = null;
	private BindingAdapter<?, T> adapter = null;

	private boolean valid;

	boolean isValid() {
		return valid;
	}

	void setValid(boolean valid) {
		this.valid = valid;
	}

	BindingSupport() {
		this.valid = true;
	}

	protected abstract Property<T> getProperty();

	private ObservableValue<?>[] dependencies = null;

	ObservableValue<?>[] getDependencies() {
		return dependencies;
	}

	private void setDependencies(ObservableValue<?>[] dependencies) {
		if (this.dependencies != null) {
			for (ObservableValue<?> dependency : this.dependencies) {
				dependency.removeListener(listener);
			}
		}
		this.dependencies = dependencies;
		if (this.dependencies != null) {
			for (ObservableValue<?> dependency : this.dependencies) {
				dependency.addListener(listener);
			}
		}
	}

	void bindReadOnly(ObservableValue<T> other,
			ObservableValue<?>[] dependencies) {
		bind(new BindingAdapter<T, T>() {
			@Override
			public T get(ObservableValue<T> other) {
				return other.getValue();
			}
		}, other, dependencies);
	}

	void bind(Property<T> other, ObservableValue<?>... dependencies) {
		bind(new BidirectionalBindingAdapter<T, T>() {
			@Override
			public T get(ObservableValue<T> other) {
				System.out.print(this + ".get");
				return other.getValue();
			}

			@Override
			public void set(Property<T> other, T value) {
				System.out.print(this + ".set");
				other.setValue(getProperty().getValue());
			}
		}, other, dependencies);
	}

	<S> void bind(BindingAdapter<S, T> adapter, ObservableValue<S> other,
			ObservableValue<?>... dependencies) {
		assert !isBound() && other != null && adapter != null;
		synchronized (this) {
			// the other value
			this.other = other;

			// a custom adapter
			this.adapter = adapter;

			// add invalidation listener
			this.other.addListener(listener);

			// set dependencies
			setDependencies(dependencies);
		}
		invalidate();
	}

	/**
	 * This method is called by {@code Property#unbound()} if the property is
	 * bound.
	 */
	void unbind() {
		assert isBound();

		// update before we un-bind
		if (!isValid()) {
			updatePropertyValue();
		}

		synchronized (this) {
			other.removeListener(listener);
			setDependencies(null);
			adapter = null;
			other = null;
		}
	}

	boolean isBound() {
		return other != null;
	}

	void invalidate() {
		if (isBound() && isValid()) {
			setValid(false);
			getProperty().notifyListeners();
		}
	}

	/**
	 * This method is called by {@code Property#invalidated()} if the property
	 * is bound and valid.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void invalidated() {
		assert isBound() && isValid();
		if (adapter instanceof BidirectionalBindingAdapter) {
			BidirectionalBindingAdapter setter = (BidirectionalBindingAdapter) adapter;
			setter.set((Property) other, getProperty().getValue());
		}
	}

	/**
	 * This method is called by {@code Property#getValue()} and
	 * {@code #unbind()} if the property is bound and invalid.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void updatePropertyValue() {
		assert isBound() && !isValid();
		getProperty().setValue(adapter.get((ObservableValue) other));
	}
}
