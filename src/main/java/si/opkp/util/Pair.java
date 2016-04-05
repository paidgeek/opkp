package si.opkp.util;

public class Pair<T, S> {

	private T first;
	private S second;

	public Pair() {
	}

	public Pair(T first, S second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass()
									 .equals(getClass())) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		Pair other = (Pair) obj;

		return first.equals(other.first) && second.equals(other.second);
	}

	@Override
	public int hashCode() {
		return first.hashCode() + (second.hashCode() << 16);
	}

}
