package cn.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.demo.dao.RedisDBTemplateDao;
import cn.demo.entity.RedisDBTemplate;
import cn.demo.service.IRedisDBTemplateService;

@Service
@Transactional
public class RedisDBTemplateService implements IRedisDBTemplateService{
	
	@Resource
	private RedisDBTemplateDao redisDBTemplateDao;
	

	@Override
	public RedisDBTemplate saveAndFlush(RedisDBTemplate entity) {
		RedisDBTemplate dbTemplate = redisDBTemplateDao.saveAndFlush(entity);
		Optional<RedisDBTemplate> optional = redisDBTemplateDao.findById(dbTemplate.getId());
		return optional.orElseThrow(RuntimeException::new);
	}
	
	@Override
	public void deleteById(Long id) {
		RedisDBTemplate dbTemplate = redisDBTemplateDao.findById(id).orElseThrow(RuntimeException::new);
		redisDBTemplateDao.delete(dbTemplate);
	}
	
	@Override
	public void deleteInBatch(Iterable<Long> ids) {
		List<RedisDBTemplate> redisTemplateList = redisDBTemplateDao.findAllById(ids);
		redisDBTemplateDao.deleteInBatch(redisTemplateList);
	}
	
	@Override
	public List<RedisDBTemplate> findAll() {
		return redisDBTemplateDao.findAll();
	}

	@Override
	public List<RedisDBTemplate> findAllById(Iterable<Long> ids) {
		return redisDBTemplateDao.findAllById(ids);
	}

	
	
}
