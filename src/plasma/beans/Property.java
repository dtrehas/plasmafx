package plasma.beans;

public abstract class Property<T> extends ReadOnlyProperty<T> implements
		WritableValue<T> {

	private BindingSupport<T> bindingSupport;

	private BindingSupport<T> getBindingSupport() {
		if (bindingSupport == null) {
			bindingSupport = new BindingSupport<T>() {
				@Override
				protected Property<T> getProperty() {
					return Property.this;
				}
			};
		}
		return bindingSupport;
	}

	public Property(T value) {
		super(value);
	}

	public Property(ValueAdapter<T> adapter) {
		super(adapter);
	}

	@Override
	public void setValue(T value) {
		if (isBound() && !isValid()) {
			bindingSupport.setValid(true);
		}
		super.setValue(value);
	}

	private void checkIfBound() {
		if (isBound()) {
			throw new IllegalStateException(getName() + " + already bound");
		}
	}

	public void bindReadOnly(ObservableValue<T> other,
			ObservableValue<?>... dependencies) {
		checkIfBound();
		BindingSupport<T> bindingSupport = getBindingSupport();
		bindingSupport.bindReadOnly(other, dependencies);
	}

	public void bind(Property<T> other, ObservableValue<?>... dependencies) {
		checkIfBound();
		BindingSupport<T> bindingSupport = getBindingSupport();
		bindingSupport.bind(other, dependencies);
	}

	public <S> void bind(BindingAdapter<S, T> adapter,
			ObservableValue<S> other, ObservableValue<?>... dependencies) {
		checkIfBound();
		BindingSupport<T> bindingSupport = getBindingSupport();
		bindingSupport.bind(adapter, other, dependencies);
	}

	public void unbind() {
		if (isBound()) {
			bindingSupport.unbind();
			bindingSupport = null;
		}
	}

	public final boolean isBound() {
		return bindingSupport != null && bindingSupport.isBound();
	}

	public final boolean isValid() {
		return bindingSupport == null || bindingSupport.isValid();
	}

	@Override
	public T getValue() {
		if (isBound() && !isValid()) {
			bindingSupport.updatePropertyValue();
		}
		return super.getValue();
	}

	@Override
	protected void notifyListeners() {
		if (isBound()) {
			if (!isValid()) {
				super.notifyListeners();
			} else {
				bindingSupport.invalidated();
			}
		} else {
			super.notifyListeners();
		}
	}
}
