package cn.demo.service;

import java.util.List;

import cn.demo.entity.RedisDBTemplate;
import cn.demo.protocal.request.RedisDBRequest;
import cn.demo.protocal.response.PageEntity;

public interface IRedisDBTemplateService {

	
	void saveAndFlush(RedisDBTemplate entity);
	
	void deleteById(Long id);
	
	void deleteInBatch(List<Long> ids);

	
	List<RedisDBTemplate> findAll();

	
	List<RedisDBTemplate> findAllById(List<Long> ids);
	
	PageEntity<RedisDBTemplate> findAllByCondition(RedisDBRequest request);
	
	RedisDBTemplate findOne(Long id);
	
}
