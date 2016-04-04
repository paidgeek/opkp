package si.opkp.model;

import org.springframework.http.ResponseEntity;

public interface NodeResult {

	ResponseEntity<NodeResult> toResponseEntity();

}
