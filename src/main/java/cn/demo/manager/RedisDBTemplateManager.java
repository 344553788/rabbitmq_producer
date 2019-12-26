package cn.demo.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.demo.dao.RedisDBTemplateDao;
import cn.demo.entity.RedisDBTemplate;
import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
@CacheConfig(cacheNames ="redisdb")
public class RedisDBTemplateManager {

	@Resource
	private RedisDBTemplateDao redisDBTemplateDao;
	

	//@CachePut将在执行完方法后（#result就能拿到返回值了）判断condition，如果返回true，则放入缓存；
	@CachePut(unless="result == null")
	public RedisDBTemplate saveAndFlush(RedisDBTemplate entity) {
		RedisDBTemplate dbTemplate = redisDBTemplateDao.saveAndFlush(entity);
		Optional<RedisDBTemplate> optional = redisDBTemplateDao.findById(dbTemplate.getId());
		return optional.orElseThrow(RuntimeException::new);
	}
	
	//@CacheEvict， beforeInvocation=false表示在方法执行之后调用（#result能拿到返回值了）；且判断condition，如果返回true，则移除缓存；
	@CacheEvict
	public void deleteById(Long id) {
		RedisDBTemplate dbTemplate = redisDBTemplateDao.findById(id).orElseThrow(RuntimeException::new);
		redisDBTemplateDao.delete(dbTemplate);
	}
	@CacheEvict
	public void deleteInBatch(Iterable<Long> ids) {
		List<RedisDBTemplate> redisTemplateList = redisDBTemplateDao.findAllById(ids);
		redisDBTemplateDao.deleteInBatch(redisTemplateList);
	}
	
	// @Cacheable(value = "user", key = "#id", condition = "#id lt 10")
	@Cacheable
	public Map<Long,StringRedisTemplate> findAll() {
		Map<Long,StringRedisTemplate> stringRedisTemplatMap = new HashMap<Long,StringRedisTemplate>();
		List<RedisDBTemplate> redisTemplates =  redisDBTemplateDao.findAll();
		if(!CollectionUtils.isEmpty(redisTemplates)) {
			redisTemplates.forEach(redisDBTemplate ->{
		        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<Object>();
		        poolConfig.setMaxIdle(redisDBTemplate.getMaxIdle());
		        poolConfig.setMinIdle(redisDBTemplate.getMinIdle());
		        poolConfig.setMaxTotal(redisDBTemplate.getMaxActive());
		        poolConfig.setMaxWaitMillis(redisDBTemplate.getMaxWait());
		        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(redisDBTemplate.getHost(), redisDBTemplate.getPort());
		        LettuceConnectionFactory lettuceConnectionFactory = null;
		        if (!StringUtils.isEmpty(redisDBTemplate.getPass())) {
		            log.info("Setting redis auth");
		            conf.setPassword(RedisPassword.of(redisDBTemplate.getPass().toCharArray()));
		            LettuceClientConfiguration  usePasswordConfig = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).useSsl().disablePeerVerification().build();
		            lettuceConnectionFactory = new LettuceConnectionFactory(conf, usePasswordConfig);
		        } else {
		        	LettuceClientConfiguration  unUsePasswordConfig = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
		        	lettuceConnectionFactory = new LettuceConnectionFactory(conf,unUsePasswordConfig);
		        }
		        StringRedisTemplate template = new StringRedisTemplate(lettuceConnectionFactory);
			    stringRedisTemplatMap.put(redisDBTemplate.getId(), template);
			});
		}
		return stringRedisTemplatMap;
	}

}
