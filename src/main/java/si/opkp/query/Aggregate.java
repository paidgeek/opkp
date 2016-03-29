package si.opkp.query;

public enum Aggregate {

	AVG,
	COUNT,
	COUNT_DISTINCT,
	MAX,
	MIN,
	SUM,
	STD,
	STD_SAMPLE,
	VAR,
	VAR_SAMPLE;

	@Override
	public String toString() {
		return super.toString()
						.toLowerCase();
	}

}