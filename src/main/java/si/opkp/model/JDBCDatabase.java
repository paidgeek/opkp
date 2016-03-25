package si.opkp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import si.opkp.query.ConditionBuilder;
import si.opkp.query.SQLConditionBuilder;
import si.opkp.util.DirectedGraph;
import si.opkp.util.Pojo;

@Component
class JDBCDatabase implements Database {

	private DirectedGraph<String, ConditionBuilder> dataGraph;
	private Validator validator;
	private Map<String, TableDefinition> definitions;
	private Connection connection;

	@Autowired
	public void setDataSource(DataSource dataSource) throws Exception {
		connection = dataSource.getConnection();
		DatabaseMetaData meta = connection.getMetaData();
		definitions = new HashMap<>();
		dataGraph = new DirectedGraph<>();

		ResultSet tables = meta.getTables(null, "%", "%", null);

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

			ResultSet pks = meta.getPrimaryKeys(null, null, tableName);
			ResultSet cols = meta.getColumns(null, "%", tableName, "%");

			while (pks.next()) {
				primaryKeyNames.add(pks.getString("COLUMN_NAME"));
			}

			while (cols.next()) {
				String name = cols.getString("COLUMN_NAME");
				FieldDefinition.Type type = null;
				boolean key = false;
				boolean notNull = cols.getInt("NULLABLE") != DatabaseMetaData.columnNullable;
				String extra = cols.getString("REMARKS");

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

				FieldDefinition fd = new FieldDefinition(name, tableName, type, notNull, key, defaultValue, extra);
				fields.put(name, fd);

				if (key) {
					primaryKeys.add(fd);
				}
			}

			definitions.put(tableName, new TableDefinition(tableName, fields, primaryKeys));
		}

		// set graph's edges based on foreign keys
		for (String tableName : dataGraph.getNodes()) {
			ResultSet fks = meta.getExportedKeys(null, null, tableName);

			while (fks.next()) {
				String pk = fks.getString("PKCOLUMN_NAME");
				String fk = fks.getString("FKCOLUMN_NAME");
				TableDefinition thisTable = definitions.get(tableName);
				TableDefinition otherTable = definitions.get(fks.getString("FKTABLE_NAME"));

				ConditionBuilder conditionBuilder = new SQLConditionBuilder()
						.equal(thisTable.getField(pk), otherTable.getField(fk));

				dataGraph.addEdge(tableName, otherTable.getName(), conditionBuilder);
			}
		}

		validator = new Validator(this);
	}

	@Override
	public DirectedGraph<String, ConditionBuilder> getDataGraph() {
		return dataGraph;
	}

	@Override
	public Validator getValidator() {
		return validator;
	}

	@Override
	public Map<String, TableDefinition> getDefinitions() {
		return definitions;
	}

	@Override
	public TableDefinition getDefinition(String table) {
		return definitions.get(table);
	}

	@Override
	public Set<String> getTables() {
		return definitions.keySet();
	}

	@Override
	public List<Pojo> queryObjects(String sql, Object... args) {
		return queryObjects(buildStatement(sql, args));
	}

	@Override
	public List<Pojo> queryObjects(String sql) {
		try {
			ResultSet rs = connection.createStatement()
											 .executeQuery(sql);

			ResultSetMetaData meta = rs.getMetaData();
			List<Pojo> objects = new ArrayList<>();

			while (rs.next()) {
				Pojo obj = new Pojo();

				for (int i = 1; i <= meta.getColumnCount(); i++) {
					obj.setProperty(meta.getColumnName(i), rs.getObject(i));
				}

				objects.add(obj);
			}

			return objects;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	@Override
	public Pojo queryObject(String sql, Object... args) {
		return queryObject(buildStatement(sql, args));
	}

	@Override
	public Pojo queryObject(String sql) {
		try {
			ResultSet rs = connection.createStatement()
											 .executeQuery(sql);

			if (!rs.next()) {
				return null;
			}

			ResultSetMetaData meta = rs.getMetaData();
			Pojo obj = new Pojo();

			for (int i = 1; i <= meta.getColumnCount(); i++) {
				obj.setProperty(meta.getColumnName(i), rs.getObject(i));
			}

			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public long update(String sql, Object... args) {
		return update(buildStatement(sql, args));
	}

	@Override
	public long update(String sql) {
		try {
			return connection.createStatement()
								  .executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private String buildStatement(String sql, Object... args) {
		StringBuilder sb = new StringBuilder();
		int i = 0;

		for (char ch : sql.toCharArray()) {
			if (ch == '?') {
				sb.append(args[i++].toString());
			} else {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

}
