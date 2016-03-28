package si.opkp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moybl.restql.RestQL;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Sequence;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

	private Util() {
	}

	public static List<String> parseStringList(String stringList) {
		if (stringList == null || stringList.isEmpty()) {
			return null;
		}

		List<String> list = new ArrayList<>();

		Collections.addAll(list, stringList.split(","));

		return list;
	}

	public static List<Integer> parseIntegerList(String integerList) {
		if (integerList == null || integerList.isEmpty()) {
			return null;
		}

		List<Integer> list = new ArrayList<>();
		String[] ints = integerList.split(",");

		for (String i : ints) {
			list.add(Integer.parseInt(i));
		}

		return list;
	}

	public static <T> T[] listToArray(List<T> list) {
		T[] array = (T[]) new Object[list.size()];

		list.toArray(array);

		return array;
	}

	public static List<Integer> integerList(int... array) {
		List<Integer> list = new ArrayList<>();

		for (int anArray : array) {
			list.add(anArray);
		}

		return list;
	}

	public static List<String> stringList(String... array) {
		List<String> list = new ArrayList<>();

		Collections.addAll(list, array);

		return list;
	}

	public static Pojo createError(String message) {
		Pojo error = new Pojo();

		error.setProperty("message", message);

		return error;
	}

	public static ResponseEntity<Pojo> responseError(HttpStatus httpStatus) {
		return responseError("undefined error", httpStatus);
	}

	public static ResponseEntity<Pojo> responseError(String message, HttpStatus httpStatus) {
		return new ResponseEntity(createError(message), httpStatus);
	}

	public static Object readJSONFile(String pathname) {
		try {
			return new ObjectMapper().readValue(new ClassPathResource(pathname).getInputStream(),
					HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return null;
	}

	public static String prettyJson(Object obj) {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter()
											 .writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static ResponseEntity<Pojo> createResult(List<Pojo> objects, long total) {
		Pojo result = new Pojo();
		Pojo meta = new Pojo();

		meta.setProperty("count", (long) objects.size());
		meta.setProperty("total", total);

		result.setProperty("meta", meta);
		result.setProperty("result", objects);

		return ResponseEntity.ok(result);
	}

	public static List<Identifier> parseQueryArguments(String arguments) {
		List<AstNode> argumentElements = RestQL.parse(arguments)
															.getElements();
		List<Identifier> queryArguments = new ArrayList<>();

		if (!argumentElements.isEmpty()) {
			AstNode arg = argumentElements.get(0);

			if (arg instanceof Sequence) {
				queryArguments = ((Sequence) arg).getElements()
															.stream()
															.map(node -> (Identifier) node)
															.collect(Collectors.toList());
			} else {
				queryArguments.add((Identifier) arg);
			}
		}

		return queryArguments;
	}

}
