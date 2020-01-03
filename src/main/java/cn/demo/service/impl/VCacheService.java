package cn.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.demo.dto.RedisKeyAndExpireDto;
import cn.demo.manager.RedisDBTemplateManager;
import cn.demo.service.IVCacheService;
import cn.demo.util.VCache;

@Service
@Transactional
public class VCacheService implements IVCacheService {

	@Resource
	private RedisDBTemplateManager redisDBTemplateManager;

	private RedisTemplate<String, Object> getRedisTemplate(Long id) {
		Map<Long, RedisTemplate<String, Object>> allMaps = redisDBTemplateManager.findAll();
		RedisTemplate<String, Object> redisTemplate = allMaps.get(id);
		if (Objects.isNull(redisTemplate)) {
			throw new RuntimeException("没有缓存信息");
		}
		return redisTemplate;
	}

	@Override
	public Object findOne(Long id, String key) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(id);
		VCache redisUtils = new VCache(redisTemplate);
		return redisUtils.get(key);
	}

	@Override
	public List<RedisKeyAndExpireDto> getKeys(Long id, String pattern) {
		List<RedisKeyAndExpireDto> lists = new ArrayList<RedisKeyAndExpireDto>();
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(id);
		VCache redisUtils = new VCache(redisTemplate);
		Set<String> keys = redisUtils.getKeys(pattern);
		if (!CollectionUtils.isEmpty(keys)) {
			keys.parallelStream().forEach(key -> {
				long expire = redisUtils.getTime(key);
				RedisKeyAndExpireDto dto = new RedisKeyAndExpireDto() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -3832706729237908498L;

					{
						setKey(key);
						setTime(expire);
					}
				};
				lists.add(dto);
			});
		}
		return lists;
	}

	@Override
	public void setCache(Long id, String key, Long expireTime) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(id);
		VCache redisUtils = new VCache(redisTemplate);
		if(Objects.isNull(expireTime) || expireTime < 0) {
			redisUtils.persist(key);
		}else {
			redisUtils.expire(key, expireTime);
		}
	}

	@Override
	public void deleteKey(Long id, String key) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(id);
		VCache redisUtils = new VCache(redisTemplate);
		redisUtils.del(key);
	}

	@Override
	public void deletePattern(Long id, String pattern) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(id);
		VCache redisUtils = new VCache(redisTemplate);
		redisUtils.batchDel(pattern);
	}

}
