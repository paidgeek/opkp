package si.opkp.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import si.opkp.util.Pojo;

public class NodeSuccessResult implements NodeResult {

	private List<Pojo> objects;
	private long count;
	private long total;

	public NodeSuccessResult() {
		objects = Collections.emptyList();
	}

	public NodeSuccessResult(List<Pojo> objects, long total) {
		this.objects = objects;
		this.count = objects.size();
		this.total = total;
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

	public void setObjects(List<Pojo> objects) {
		this.objects = objects;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public ResponseEntity<NodeResult> toResponseEntity() {
		return new ResponseEntity<>(this, HttpStatus.OK);
	}

}
