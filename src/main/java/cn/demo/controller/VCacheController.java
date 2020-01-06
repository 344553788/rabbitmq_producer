package cn.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.dto.RedisDBIDAndRedisKey;
import cn.demo.dto.RedisKeyAndExpireDto;
import cn.demo.service.IVCacheService;

@RestController
@RequestMapping("/cache")
public class VCacheController {
	
	@Autowired
	private IVCacheService vCacheService;

	
	@PostMapping("/setTimeByKey")
	ResponseEntity<Void> setTimeByKey(@RequestBody RedisDBIDAndRedisKey query) {
		vCacheService.setCache(query.getId(),query.getRedisKey(),query.getTime());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/keys")
	ResponseEntity<List<RedisKeyAndExpireDto>> queryRedisKeys(@RequestBody RedisDBIDAndRedisKey query) {
		return ResponseEntity.ok(vCacheService.getKeys(query.getId(),query.getRedisKey()));
	}
	
	@GetMapping("/detail")
	ResponseEntity<Object> findRedisValueByKey(@RequestBody RedisDBIDAndRedisKey query) {
		return ResponseEntity.ok(vCacheService.findOne(query.getId(),query.getRedisKey()));
	}
	
	@DeleteMapping("/deleteKey")
	ResponseEntity<Void> deleteByKey(@RequestBody RedisDBIDAndRedisKey query) {
		vCacheService.deleteKey(query.getId(),query.getRedisKey());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/deletePattern")
	ResponseEntity<Void> deletePattern(@RequestBody RedisDBIDAndRedisKey query) {
		vCacheService.deletePattern(query.getId(),query.getRedisKey());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
