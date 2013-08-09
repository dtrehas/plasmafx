package plasma.beans;

public interface BindingAdapter<T1,T2> {
	T2 get(ObservableValue<T1> other);
}
