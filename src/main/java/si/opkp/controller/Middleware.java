package si.opkp.controller;

import com.moybl.restql.ast.Query;

import org.springframework.http.ResponseEntity;

import si.opkp.util.Pojo;

public interface Middleware {

	ResponseEntity<Pojo> get(Query query);

}
