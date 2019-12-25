package cn.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.RedisDTO;
import cn.demo.manager.RedisTemplateManager;

@RestController
@RequestMapping("/redis")
public class RedisManagerController {

	@Autowired
	private RedisTemplateManager redisTemplateManager;
	
	@GetMapping
	public List<RedisDTO> getRedisKeys(){
		List<RedisDTO> lists =  redisTemplateManager.getRedisKeys();
		return lists;
	}
	
	@PostMapping
	public RedisDTO getRedisValueByKey(RedisDTO redisDTO){
		return redisTemplateManager.getRedisValue(redisDTO);
	}
	
	@DeleteMapping
	public void deleteRedisValueByKey(@RequestBody RedisDTO redisDTO){
		redisTemplateManager.deleteRedisKey(redisDTO);
	}
}
