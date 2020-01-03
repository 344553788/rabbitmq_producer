package cn.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.demo.dao.RedisDBTemplateDao;
import cn.demo.entity.RedisDBTemplate;
import cn.demo.manager.RedisDBTemplateManager;
import cn.demo.service.IRedisDBTemplateService;

@Service
@Transactional
public class RedisDBTemplateService implements IRedisDBTemplateService{
	
	@Resource
	private RedisDBTemplateManager redisDBTemplateManager;
	
	@Resource
	private RedisDBTemplateDao redisDBTemplateDao;
	

	@Override
	public void saveAndFlush(RedisDBTemplate entity) {
		redisDBTemplateManager.saveAndFlush(entity);
	}
	
	@Override
	public void deleteById(Long id) {
		redisDBTemplateManager.deleteById(id);
	}
	
	@Override
	public void deleteInBatch(List<Long> ids) {
		redisDBTemplateManager.deleteInBatch(ids);
	}
	
	
	@Override
	public List<RedisDBTemplate> findAll() {
		return redisDBTemplateManager.findAllRedisDB();
	}

	@Override
	public List<RedisDBTemplate> findAllById(List<Long> ids) {
		return redisDBTemplateDao.findAllById(ids);
	}

	@Override
	public RedisDBTemplate findOne(Long id) {
		return redisDBTemplateDao.findById(id).orElse(null);
	}
}
