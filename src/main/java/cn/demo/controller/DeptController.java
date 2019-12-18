package cn.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.entity.Dept;
import cn.demo.service.DeptService;

@RequestMapping("/dept")
@RestController
public class DeptController {

	
	@Autowired
	private DeptService deptService;
	
	@PostMapping
	public ResponseEntity<List<Dept>> saveDepts(@RequestBody List<Dept> depts) {
		List<Dept> deptList = deptService.saveAll(depts);
		return ResponseEntity.of(Optional.of(deptList));
	}

	@GetMapping
	public ResponseEntity<List<Dept>> query() {
		List<Dept> deptList = deptService.findAll();
		return ResponseEntity.of(Optional.of(deptList));
	}
}
