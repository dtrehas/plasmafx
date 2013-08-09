package plasma.beans;

/**
 * This class provides a full implementation of a {@link ReadOnlyProperty}
 * wrapping an arbitrary {@code Object}.
 * 
 * @param <T>
 *            the type of the wrapped {@code Object}
 */
public class SimpleReadOnlyProperty<T> extends ReadOnlyProperty<T> {

	public SimpleReadOnlyProperty(T value) {
		super(value);
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
