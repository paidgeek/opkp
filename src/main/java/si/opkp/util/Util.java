package si.opkp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

}
