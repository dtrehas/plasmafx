package plasma.beans;

public interface ValueAdapter<T> {
	T get();

	void set(T value);
}
