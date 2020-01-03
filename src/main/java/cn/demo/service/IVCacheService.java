package cn.demo.service;

import java.util.List;

import cn.demo.dto.RedisKeyAndExpireDto;

public interface IVCacheService {

	Object findOne(Long id,String key);
	
	List<RedisKeyAndExpireDto> getKeys(Long id,String pattern);
	
	void setCache(Long id,String key,Long expireTime);
	
	void deleteKey(Long id, String key);
	
	void deletePattern(Long id, String key);
	
}
