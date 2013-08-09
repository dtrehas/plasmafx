package plasma.beans;

/**
 * This class provides a full implementation of a {@link Property} wrapping an
 * arbitrary {@code Object}.
 * 
 * @param <T>
 *            the type of the wrapped {@code Object}
 */
public class SimpleProperty<T> extends Property<T> {

	public SimpleProperty(T value) {
		super(value);
	}
	
	public SimpleProperty(ValueAdapter<T> adapter) {
		super(adapter);
	}

	@Override
	public Object getBean() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

}
