package plasma.beans;

public interface WritableValue<T> {

	T getValue();

	void setValue(T value);

}
