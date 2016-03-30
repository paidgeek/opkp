package si.opkp.controller;

import com.moybl.restql.ast.AstNode;

import org.springframework.http.ResponseEntity;

import java.util.List;

import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

interface Controller {

	ResponseEntity<?> get(List<AstNode> arguments, RequestParams params);

	ResponseEntity<?> post(List<AstNode> arguments, RequestParams params, Pojo body);

}
