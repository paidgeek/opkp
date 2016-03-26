package si.opkp.query;

import java.util.HashMap;
import java.util.Map;

import si.opkp.util.Aggregate;

public class Field {

	private static final Map<String, Aggregate> SUFFIXES = new HashMap<String, Aggregate>() {{
		put("$avg", Aggregate.AVG);
		put("$count", Aggregate.COUNT);
		put("$count_distinct", Aggregate.COUNT_DISTINCT);
		put("$max", Aggregate.MAX);
		put("$min", Aggregate.MIN);
		put("$sum", Aggregate.SUM);
		put("$std", Aggregate.STD);
		put("$std_sample", Aggregate.STD_SAMPLE);
		put("$var", Aggregate.VAR);
		put("$var_sample", Aggregate.VAR_SAMPLE);
	}};

	private String name;
	private Aggregate aggregate;

	public Field(String name) {
		this.name = name;
		this.aggregate = Aggregate.NONE;

		SUFFIXES.keySet()
				  .stream()
				  .filter(name::endsWith)
				  .forEach(key -> {
					  aggregate = SUFFIXES.get(key);
					  this.name = name.substring(0, name.length() - key.length());
				  });
	}

	public Field(String name, Aggregate aggregate) {
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
