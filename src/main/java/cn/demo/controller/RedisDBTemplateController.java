package cn.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.entity.RedisDBTemplate;
import cn.demo.service.IRedisDBTemplateService;

@RestController
@RequestMapping("/redis")
public class RedisDBTemplateController {

	@Autowired
	private IRedisDBTemplateService redisDBTemplateService;

	@PostMapping
	public ResponseEntity<Void> saveAndFlush(@RequestBody RedisDBTemplate entity) {
		redisDBTemplateService.saveAndFlush(entity);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/ids")
	ResponseEntity<List<RedisDBTemplate>> findAllByIds(@RequestBody List<Long> ids) {
		return ResponseEntity.ok(redisDBTemplateService.findAllById(ids));
	}
	
	@GetMapping("/{id}")
	ResponseEntity<RedisDBTemplate> findOne(@PathVariable Long id) {
		return ResponseEntity.ok(redisDBTemplateService.findOne(id));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteById(@PathVariable Long id) {
		redisDBTemplateService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping
	ResponseEntity<List<RedisDBTemplate>> findAll() {
		return ResponseEntity.ok(redisDBTemplateService.findAll());
	}
	@DeleteMapping
	ResponseEntity<Void> deleteInBatch(@RequestBody List<Long> ids) {
		redisDBTemplateService.deleteInBatch(ids);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
