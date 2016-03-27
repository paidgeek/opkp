package si.opkp.controller;

import org.springframework.http.ResponseEntity;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

public interface Middleware {

	ResponseEntity<Pojo> get(String[] model, RequestParams params);

}
