package cn.demo.service;

import java.util.List;

import cn.demo.entity.RedisDBTemplate;

public interface IRedisDBTemplateService {

	
	void saveAndFlush(RedisDBTemplate entity);
	
	void deleteById(Long id);
	
	void deleteInBatch(List<Long> ids);

	
	List<RedisDBTemplate> findAll();

	
	List<RedisDBTemplate> findAllById(List<Long> ids);
	
	RedisDBTemplate findOne(Long id);
	
}
