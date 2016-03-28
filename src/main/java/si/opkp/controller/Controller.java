package si.opkp.controller;

import com.moybl.restql.ast.Identifier;

import org.springframework.http.ResponseEntity;

import java.util.List;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

interface Controller {

	ResponseEntity<Pojo> get(List<Identifier> arguments, RequestParams params);

	ResponseEntity<Pojo> post(List<Identifier> arguments, RequestParams params, Pojo body);

}
