package cn.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.entity.RedisDBTemplate;
import cn.demo.protocal.request.RedisDBRequest;
import cn.demo.protocal.response.PageEntity;
import cn.demo.protocal.response.RestResponse;
import cn.demo.service.IRedisDBTemplateService;

@RestController
@RequestMapping("/redis")
public class RedisDBTemplateController {

	@Autowired
	private IRedisDBTemplateService redisDBTemplateService;

	@PostMapping
	public RestResponse<Void> saveAndFlush(@RequestBody RedisDBTemplate entity) {
		redisDBTemplateService.saveAndFlush(entity);
		return RestResponse.success();
	}
	
	@GetMapping("/query")
	RestResponse<PageEntity<RedisDBTemplate>> findAllByCondition(Pageable pageable,@RequestBody RedisDBRequest request) {
		
		return RestResponse.success(redisDBTemplateService.findAllByCondition(request));
	}
	
	@GetMapping("/{id}")
	RestResponse<RedisDBTemplate> findOne(@PathVariable Long id) {
		return RestResponse.success(redisDBTemplateService.findOne(id));
	}
	
	@DeleteMapping("/{id}")
	RestResponse<Void> deleteById(@PathVariable Long id) {
		redisDBTemplateService.deleteById(id);
		return RestResponse.success();
	}
	
	@GetMapping
	RestResponse<List<RedisDBTemplate>> findAll() {
		return RestResponse.success(redisDBTemplateService.findAll());
	}
	@DeleteMapping
	RestResponse<Void> deleteInBatch(@RequestBody List<Long> ids) {
		redisDBTemplateService.deleteInBatch(ids);
		return RestResponse.success();
	}
}
