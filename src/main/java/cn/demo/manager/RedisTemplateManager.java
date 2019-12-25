package cn.demo.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import cn.demo.RedisDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisTemplateManager {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public List<RedisDTO> getRedisKeys() {
		Set<String> keys = stringRedisTemplate.keys("*");
		log.info("redis keys size is {}",keys.size());
		List<RedisDTO> lists = new ArrayList<RedisDTO>();
		if(!CollectionUtils.isEmpty(keys)) {
			keys.stream().forEach(key->{
				Long timeout = stringRedisTemplate.getExpire(key);
				RedisDTO redisDTO = RedisDTO.builder().key(key).timeout(timeout).build();
				lists.add(redisDTO);
			});
		}
		return lists;
	}

	public void deleteRedisKey(RedisDTO redisDTO) {
		String key = redisDTO.getKey();
		Object value = stringRedisTemplate.opsForValue().get(key);
		if (!Objects.isNull(value)) {
			stringRedisTemplate.delete(key);
		}
	}

	public void deletePrefixRedisKey(RedisDTO redisDTO) {
		String key = redisDTO.getKey();
		Set<String> keys = stringRedisTemplate.keys(key + "*");
		if (!CollectionUtils.isEmpty(keys)) {
			for (String redisKey : keys) {
				Object value = stringRedisTemplate.opsForValue().get(redisKey);
				if (!Objects.isNull(value)) {
					stringRedisTemplate.delete(key);
				}
			}
		}

	}

	public void setExpiresRedisKey(RedisDTO redisDTO) {
		String key = redisDTO.getKey();
		Long timeout = redisDTO.getTimeout();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (!Objects.isNull(value)) {
			stringRedisTemplate.opsForValue().set(key, value, timeout);
		}
	}

	public RedisDTO getRedisValue(RedisDTO redisDTO) {
		String key = redisDTO.getKey();
		Long timeout = stringRedisTemplate.getExpire(key);
		String value = stringRedisTemplate.opsForValue().get(key);
		return RedisDTO.builder().key(key).value(value).timeout(timeout).build();
	}
}
