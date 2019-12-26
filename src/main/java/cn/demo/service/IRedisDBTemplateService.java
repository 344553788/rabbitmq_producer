package cn.demo.service;

import java.util.List;

import cn.demo.entity.RedisDBTemplate;

public interface IRedisDBTemplateService {

	
	RedisDBTemplate saveAndFlush(RedisDBTemplate entity);
	
	void deleteById(Long id);
	
	void deleteInBatch(Iterable<Long> ids);

	
	List<RedisDBTemplate> findAll();

	
	List<RedisDBTemplate> findAllById(Iterable<Long> ids);


	
}
