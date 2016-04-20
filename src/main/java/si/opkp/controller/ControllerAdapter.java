package si.opkp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

public abstract class ControllerAdapter {

	public ResponseEntity<?> get(String[] path, RequestParams params) {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> post(String[] path, Pojo body) {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
