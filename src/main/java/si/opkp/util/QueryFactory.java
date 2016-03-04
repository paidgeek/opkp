package si.opkp.util;

import com.moybl.restql.RestQL;
import com.moybl.restql.RestQLLexer;
import com.moybl.restql.RestQLParser;
import com.moybl.restql.generators.SQLGenerator;
import si.opkp.util.RelationMap;
import si.opkp.util.SQLSelectBuilder;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

public class QueryFactory {

	public static String count(List<String> tables, List<String> joins, String query) {
		return select(Arrays.asList("COUNT(*) as count"), tables, joins, query, null, null);
	}

	public static String select(List<String> columns, List<String> tables, List<String> joins, String query, List<String> orderBy, List<Long> limit) {
		SQLSelectBuilder selectBuilder = new SQLSelectBuilder(columns);
		RelationMap relationMap = RelationMap.getInstance();

		selectBuilder.from(tables.get(0));

		for (int i = 1; i < tables.size(); i++) {
			String a = tables.get(i - 1);
			String b = tables.get(i);

			String edge = relationMap.getEdge(a, b);

			if (edge == null) {
				return null;
			}

			selectBuilder.join(b, joins.get(i - 1), edge);
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

		return selectBuilder.build();
	}

}
