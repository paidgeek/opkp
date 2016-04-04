package si.opkp.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NodeErrorResult implements NodeResult {

	private HttpStatus status;
	private String message;

	public NodeErrorResult(String message) {
		this(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public NodeErrorResult(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public int getStatusCode() {
		return status.value();
	}

	public String getMessage() {
		return message;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public ResponseEntity<NodeResult> toResponseEntity() {
		return new ResponseEntity<>(this, status);
	}

}
