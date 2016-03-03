package si.opkp.model;

import com.moybl.restql.RestQLLexer;
import com.moybl.restql.RestQLParser;
import com.moybl.restql.generators.SQLGenerator;
import si.opkp.util.RelationMap;
import si.opkp.util.SQLSelectBuilder;

import java.io.ByteArrayInputStream;

public class QueryFactory {

	public static String select(String[] fields, String[] tables, String[] joins, String query, String[] orderBy, int[] boundary) {
		SQLSelectBuilder selectBuilder = new SQLSelectBuilder(fields);
		RelationMap relationMap = RelationMap.getInstance();

		selectBuilder.from(tables[0]);

		for (int i = 1; i < tables.length; i++) {
			String a = tables[i - 1];
			String b = tables[i];
			String edge = relationMap.getEdge(a, b);

			if (edge == null) {
				return null;
			}

			selectBuilder.join(b, joins[i - 1], edge);
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

		selectBuilder.limit(boundary);

		return selectBuilder.build();
	}

}
