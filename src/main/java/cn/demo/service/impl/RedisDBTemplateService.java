package cn.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.demo.dao.RedisDBTemplateDao;
import cn.demo.entity.RedisDBTemplate;
import cn.demo.manager.RedisDBTemplateManager;
import cn.demo.protocal.request.RedisDBRequest;
import cn.demo.protocal.response.PageEntity;
import cn.demo.service.IRedisDBTemplateService;

@Service
@Transactional
public class RedisDBTemplateService implements IRedisDBTemplateService{
	
	@Resource
	private RedisDBTemplateManager redisDBTemplateManager;
	
	@Resource
	private RedisDBTemplateDao redisDBTemplateDao;
	
	@Resource
	private ModelMapper  modelMapper;
	

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

	@Override
	public PageEntity<RedisDBTemplate> findAllByCondition(RedisDBRequest request) {
		// 排序方式，这里是以“recordNo”为标准进行降序
        RedisDBTemplate template = modelMapper.map(request, RedisDBTemplate.class);
        Example<RedisDBTemplate> example = Example.of(template);
        PageRequest page = PageRequest.of(request.getPageNum() > 0 ? request.getPageNum()-1 : 0, request.getPageSize(),Sort.by(Direction.DESC, "id"));
        Page<RedisDBTemplate>  pageResult = redisDBTemplateDao.findAll(example,page);
        PageEntity<RedisDBTemplate>  pageEntity = 	PageEntity.convert(pageResult);
		return pageEntity;
	}
}
