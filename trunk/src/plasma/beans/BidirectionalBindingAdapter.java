package plasma.beans;

public interface BidirectionalBindingAdapter<T1,T2> extends BindingAdapter<T1,T2> {
	void set(Property<T1> other, T2 value);
}
