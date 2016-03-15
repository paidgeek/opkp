package si.opkp.util;

import com.moybl.restql.*;

import java.util.*;

public class QueryFactory {

	public static Optional<String> count(String table, String query) {
		return select(Arrays.asList("COUNT(*) as count"), table, query, null, null);
	}

	public static Optional<String> count(List<String> tables, String query) {
		return select(Arrays.asList("COUNT(*) as count"), tables, query, null, null);
	}

	public static Optional<String> select(List<String> columns, String table, String query, List<String> orderBy, List<Integer> limit) {
		SQLSelectBuilder selectBuilder = new SQLSelectBuilder(columns);

		selectBuilder.from(table);

		if (query != null) {
			selectBuilder.where(RestQL.parseToSQL(query));
		}

		if (orderBy != null) {
			selectBuilder.orderByPrefixedColumns(orderBy);
		}

		if (limit != null) {
			selectBuilder.limit(limit);
		}

		return Optional.of(selectBuilder.build());
	}

	public static Optional<String> select(List<String> columns, List<String> tables, String query, List<String> orderBy, List<Integer> limit) {
		SQLSelectBuilder selectBuilder = new SQLSelectBuilder(columns);
		RelationMap relationMap = RelationMap.getInstance();

		selectBuilder.from(tables.get(0));

		for (int i = 1; i < tables.size(); i++) {
			String a = tables.get(i - 1);
			String b = tables.get(i);

			String edge = relationMap.getEdge(a, b);

			if (edge == null) {
				return Optional.empty();
			}

			selectBuilder.join(b, edge);
		}

		if (query != null) {
			selectBuilder.where(RestQL.parseToSQL(query));
		}

		if (orderBy != null) {
			selectBuilder.orderByPrefixedColumns(orderBy);
		}

		if (limit != null) {
			selectBuilder.limit(limit);
		}

		return Optional.of(selectBuilder.build());
	}

}
