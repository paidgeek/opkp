package si.opkp.util;

public class RequestColumn {

	private static final String COUNT_SUFFIX = "$count";

	private String name;
	private Aggregate aggregate;

	public RequestColumn(String name) {
		if (name.endsWith(COUNT_SUFFIX)) {
			aggregate = Aggregate.COUNT;
			this.name = name.substring(0, name.length() - COUNT_SUFFIX.length());
		} else {
			aggregate = Aggregate.NONE;
			this.name = name;
		}
	}

	public RequestColumn(String name, Aggregate aggregate) {
		this.name = name;
		this.aggregate = aggregate;
	}

	public String getName() {
		return name;
	}

	public Aggregate getAggregate() {
		return aggregate;
	}

}
