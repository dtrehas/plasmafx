package plasma.beans;

public abstract class ReadOnlyProperty<T> extends ObservableValueBase<T> {

	public ReadOnlyProperty(T value) {
		super(value);
	}
	
	public ReadOnlyProperty(ValueAdapter<T> adapter) {
		super(adapter);
	}

	public abstract Object getBean();

	public abstract String getName();
	
}
