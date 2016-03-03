package si.opkp.model;

import com.moybl.restql.RestQLLexer;
import com.moybl.restql.RestQLParser;
import com.moybl.restql.generators.SQLGenerator;
import si.opkp.util.RelationMap;
import si.opkp.util.SQLSelectBuilder;

import java.io.ByteArrayInputStream;
import java.util.List;

public class QueryFactory {

	public static String select(List<String> columns, List<String> tables, List<String> joins, String query, List<String> orderBy, List<Integer> limit) {
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
			SQLGenerator sqlGenerator = new SQLGenerator();
			new RestQLParser().parse(new RestQLLexer(new ByteArrayInputStream(query.getBytes()))).accept(sqlGenerator);
			query = sqlGenerator.getResult();

			selectBuilder.where(query);
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
