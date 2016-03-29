package si.opkp.model;

import com.moybl.restql.Token;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.factory.RestQLBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import si.opkp.query.SelectBuilder;
import si.opkp.util.Graph;
import si.opkp.util.Pojo;

@Component
class MySQLDatabase implements Database {

	private static final Logger logger = Logger.getLogger("Database");

	private Graph<String, AstNode> dataGraph;
	private Map<String, ModelDefinition> definitions;
	private Connection connection;

	@Autowired
	public void setDataSource(DataSource dataSource) throws Exception {
		connection = dataSource.getConnection();
		DatabaseMetaData meta = connection.getMetaData();
		definitions = new HashMap<>();
		dataGraph = new Graph<>();

		ResultSet tables = meta.getTables(null, meta.getUserName(), "%", null);

		while (tables.next()) {
			String tableName = tables.getString("TABLE_NAME")
											 .toLowerCase();

			// ignore 'sqlite*'
			if (tableName.startsWith("sqlite")) {
				continue;
			}

			dataGraph.addNode(tableName);

			Map<String, FieldDefinition> fields = new HashMap<>();
			Set<FieldDefinition> primaryKeys = new HashSet<>();
			Set<String> primaryKeyNames = new HashSet<>();

			ResultSet pks = meta.getPrimaryKeys(null, meta.getUserName(), tableName);
			ResultSet cols = meta.getColumns(null, meta.getUserName(), tableName, "%");

			while (pks.next()) {
				primaryKeyNames.add(pks.getString("COLUMN_NAME"));
			}

			while (cols.next()) {
				String name = cols.getString("COLUMN_NAME");
				FieldDefinition.Type type = null;
				boolean key = false;
				boolean notNull = cols.getInt("NULLABLE") != DatabaseMetaData.columnNullable;

				if (primaryKeyNames.contains(name)) {
					key = true;
				}

				switch (cols.getInt("DATA_TYPE")) {
					case Types.INTEGER:
						type = FieldDefinition.Type.INTEGER;
						break;
					case Types.DATE:
						type = FieldDefinition.Type.DATETIME;
						break;
					case Types.NVARCHAR:
					case Types.VARCHAR:
						type = FieldDefinition.Type.STRING;
						break;
					case Types.DECIMAL:
						type = FieldDefinition.Type.DECIMAL;
						break;
				}

				//Object defaultValue = cols.getString("COLUMN_DEF");
				Object defaultValue = null;

				FieldDefinition fd = new FieldDefinition(name, tableName, type, notNull, key, defaultValue);
				fields.put(name, fd);

				if (key) {
					primaryKeys.add(fd);
				}
			}

			definitions.put(tableName, new ModelDefinition(tableName, fields, primaryKeys));
		}

		// set graph's edges based on foreign keys
		for (String tableName : dataGraph.getNodes()) {
			ResultSet fks = meta.getExportedKeys(null, meta.getUserName(), tableName);

			while (fks.next()) {
				String pk = fks.getString("PKCOLUMN_NAME");
				String fk = fks.getString("FKCOLUMN_NAME");
				ModelDefinition thisTable = definitions.get(tableName);
				ModelDefinition otherTable = definitions.get(fks.getString("FKTABLE_NAME"));

				RestQLBuilder b = new RestQLBuilder();
				dataGraph.addEdge(tableName, otherTable.getName(), b.binaryOperation(b.member(b.identifier(tableName), b.identifier(pk)),
						Token.EQUAL,
						b.member(b.identifier(otherTable.getName()), b.identifier(fk))));

				//dataGraph.addEdge(tableName, otherTable.getName(), conditionBuilder);
			}
		}
	}

	@Override
	public Graph<String, AstNode> getDataGraph() {
		return dataGraph;
	}

	@Override
	public Map<String, ModelDefinition> getModels() {
		return definitions;
	}

	@Override
	public QueryResult queryObjects(SelectBuilder selectBuilder, Object... args) {
		try {
			String stmt = buildStatement(selectBuilder.build(), args);
			logger.log(Level.INFO, "Executing statement:\n" + stmt);
			ResultSet rs = connection.createStatement()
											 .executeQuery(stmt);

			ResultSetMetaData meta = rs.getMetaData();
			List<Pojo> objects = new ArrayList<>();

			while (rs.next()) {
				Pojo obj = new Pojo();

				for (int i = 1; i <= meta.getColumnCount(); i++) {
					obj.setProperty(meta.getColumnName(i), rs.getObject(i));
				}

				objects.add(obj);
			}

			return QueryResult.result(objects, -1);
		} catch (SQLException e) {
			return QueryResult.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public QueryResult callFunction(String function, Object... args) {
		return null;
	}

	private String buildStatement(String statement, Object... args) {
		if (args == null || args.length == 0) {
			return statement;
		}

		StringBuilder sb = new StringBuilder();
		int i = 0;

		for (char ch : statement.toCharArray()) {
			if (ch == '?') {
				sb.append(args[i++].toString());
			} else {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

}
