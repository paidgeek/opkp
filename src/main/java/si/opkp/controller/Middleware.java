package si.opkp.controller;

import org.springframework.http.ResponseEntity;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

public interface Middleware {

	ResponseEntity<?> get(ControllerAdapter controllerAdapter, String[] path, RequestParams params);

	ResponseEntity<?> post(ControllerAdapter controllerAdapter,String[] path, Pojo body);

}
