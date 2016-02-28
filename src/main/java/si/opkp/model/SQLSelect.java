package si.opkp.model;

public class SQLSelect {

	private String expr;
	private String from;
	private String where;
	private String groupBy;
	private String having;
	private String orderBy;
	private int offset;
	private int count;

	public SQLSelect() {
		expr = "";
		from = "";
		where = "";
		groupBy = "";
		having = "";
		orderBy = "";
		offset = 0;
		count = Integer.MAX_VALUE;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getHaving() {
		return having;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatement() {
		String sql = "SELECT";

		if (expr.isEmpty()) {
			sql += " *";
		} else {
			sql += " " + expr;
		}

		sql += " FROM " + from;

		if (!where.isEmpty()) {
			sql += " WHERE " + where;
		}

		if (!groupBy.isEmpty()) {
			sql += " GROUP BY " + groupBy;
		}

		if (!having.isEmpty()) {
			sql += " HAVING " + having;
		}

		if (!orderBy.isEmpty()) {
			sql += " ORDER BY " + orderBy;
		}

		sql += " LIMIT " + offset + "," + count;

		return sql;
	}

	public static class Builder {

		private SQLSelect sqlSelect;

		public Builder() {
			sqlSelect = new SQLSelect();
		}

		public Builder expr(String from) {
			sqlSelect.setFrom(from);

			return this;
		}

		public Builder from(String from) {
			sqlSelect.setFrom(from);

			return this;
		}

		public Builder where(String where) {
			sqlSelect.setFrom(where);

			return this;
		}

		public Builder groupBy(String groupBy) {
			sqlSelect.setFrom(groupBy);

			return this;
		}

		public Builder having(String having) {
			sqlSelect.setFrom(having);

			return this;
		}

		public Builder orderBy(String orderBy) {
			sqlSelect.setFrom(orderBy);

			return this;
		}

		public Builder offset(int offset) {
			sqlSelect.setOffset(offset);

			return this;
		}

		public Builder count(int count) {
			sqlSelect.setCount(count);

			return this;
		}

		public SQLSelect build() {
			return sqlSelect;
		}

	}

}
