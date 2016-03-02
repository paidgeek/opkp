package si.opkp.util;

public class Util {

	private Util() {
	}

	public static String[] parseFieldList(String fields) {
		if (fields == null || fields.isEmpty()) {
			return null;
		}

		return fields.split(",");
	}

	public static int[] parseLimit(String limit) {
		String[] bounds = limit.split(",");

		if (bounds.length == 1) {
			return new int[]{
					Integer.parseInt(bounds[0])
			};
		} else {
			return new int[]{
					Integer.parseInt(bounds[0]),
					Integer.parseInt(bounds[1])
			};
		}
	}

}
