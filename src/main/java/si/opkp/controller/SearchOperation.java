package si.opkp.controller;

public enum SearchOperation {

	EQUALITY, NEGATION, GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;

	public static final String[] OPERATION_SET = {":", "!", ">", ">:", "<", "<:", "~"};

	public static SearchOperation getOperation(String input) {
		if (input.equals(":")) {
			return EQUALITY;
		} else if (input.equals("!")) {
			return NEGATION;
		} else if (input.equals(">")) {
			return NEGATION;
		} else if (input.equals(">:")) {
			return NEGATION;
		} else if (input.equals("<")) {
			return NEGATION;
		} else if (input.equals("<:")) {
			return NEGATION;
		} else if (input.equals("~")) {
			return NEGATION;
		}

		return null;
	}

}
