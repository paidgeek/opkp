package si.opkp;

import com.moybl.restql.Token;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.factory.RestQLBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import si.opkp.model.*;
import si.opkp.query.SelectBuilder;
import si.opkp.util.Graph;
import si.opkp.util.Pojo;

@Component
@Primary
class DatabaseMock implements Database {

	private Graph<String, AstNode> dataGraph;
	private Map<String, ModelDefinition> definitions;
	private Map<String, FunctionDefinition> functions;
	private Connection connection;

	@Autowired
	public void setDataSource(DataSource dataSource) throws Exception {
		connection = dataSource.getConnection();
		DatabaseMetaData meta = connection.getMetaData();
		definitions = new HashMap<>();
		functions = new HashMap<>();
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
				FieldType type = FieldType.fromSQLType(cols.getInt("DATA_TYPE"));
				boolean key = false;
				boolean notNull = cols.getInt("NULLABLE") != DatabaseMetaData.columnNullable;

				if (primaryKeyNames.contains(name)) {
					key = true;
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

		// set functions
		ResultSet funcs = meta.getFunctions(null, meta.getUserName(), "%");

		while (funcs.next()) {
			String name = funcs.getString("FUNCTION_NAME");
			List<ParameterDefinition> parameters = new ArrayList<>();

			ResultSet funcCols = meta.getFunctionColumns(null, meta.getUserName(), name, "%");

			while (funcCols.next()) {
				String pName = funcCols.getString("COLUMN_NAME");
				FieldType pType = FieldType.fromSQLType(funcCols.getInt("COLUMN_TYPE"));

				parameters.add(new ParameterDefinition(pName, pType));
			}

			functions.put(name, new FunctionDefinition(name, parameters));
		}

		// set procedures
		ResultSet procs = meta.getProcedures(null, meta.getUserName(), "%");

		while (procs.next()) {
			String name = procs.getString("PROCEDURE_NAME");
			List<ParameterDefinition> parameters = new ArrayList<>();

			ResultSet procCols = meta.getProcedureColumns(null, meta.getUserName(), name, "%");

			while (procCols.next()) {
				String pName = procCols.getString("COLUMN_NAME");
				FieldType pType = FieldType.fromSQLType(procCols.getInt("DATA_TYPE"));

				parameters.add(new ParameterDefinition(pName, pType));
			}

			functions.put(name, new FunctionDefinition(name, parameters));
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
	public Map<String, FunctionDefinition> getFunctions() {
		return functions;
	}

	@Override
	public QueryResult queryObjects(SelectBuilder selectBuilder, Object... args) {
		try {
			ResultSet rs = connection.createStatement()
											 .executeQuery(buildStatement(selectBuilder.build(), args));

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
			e.printStackTrace();

			return QueryResult.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public QueryResult callFunction(String function, Object... args) {
		List<Pojo> result = new ArrayList<>();

		if (function.equals("ping")) {
			result.add(new Pojo.Builder()
					.property("ping", "pong")
					.build());
		}

		return QueryResult.result(result, 1);
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