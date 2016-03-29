package si.opkp.model;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import si.opkp.util.Pojo;

public class QueryResult {

	private List<Pojo> objects;
	private long count;
	private long total;
	private HttpStatus status;
	private String message;

	private QueryResult() {
		message = "";
		objects = Collections.emptyList();
		status = HttpStatus.OK;
	}

	public List<Pojo> getObjects() {
		return objects;
	}

	public long getCount() {
		return count;
	}

	public long getTotal() {
		return total;
	}

	public int getStatus() {
		return status.value();
	}

	public String getMessage() {
		return message;
	}

	public static QueryResult result(List<Pojo> objects, long total) {
		QueryResult qr = new QueryResult();

		qr.objects = objects;
		qr.count = objects.size();
		qr.total = total;

		return qr;
	}

	public static QueryResult error(String message) {
		return error(message, HttpStatus.BAD_REQUEST);
	}

	public static QueryResult error(String message, HttpStatus status) {
		QueryResult qr = new QueryResult();

		qr.message = message;
		qr.status = status;

		return qr;
	}

}
