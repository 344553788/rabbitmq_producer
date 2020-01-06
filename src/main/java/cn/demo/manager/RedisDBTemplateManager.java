package cn.demo.manager;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import cn.demo.config.RedisTemplateConfig;
import cn.demo.dao.RedisDBTemplateDao;
import cn.demo.entity.RedisDBTemplate;
import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
@CacheConfig(cacheNames="redisdb")
public class RedisDBTemplateManager {

	@Resource
	private RedisDBTemplateDao redisDBTemplateDao;

	@CacheEvict(key = "'template'")
	public RedisDBTemplate saveAndFlush(RedisDBTemplate entity) {
		RedisDBTemplate dbTemplate = redisDBTemplateDao.saveAndFlush(entity);
		Optional<RedisDBTemplate> optional = redisDBTemplateDao.findById(dbTemplate.getId());
		return optional.orElseThrow(RuntimeException::new);
	}
	
	@CacheEvict(key = "'template'")
	public void deleteById(Long id) {
		RedisDBTemplate dbTemplate = redisDBTemplateDao.findById(id).orElseThrow(RuntimeException::new);
		redisDBTemplateDao.delete(dbTemplate);
	}
	
	@CacheEvict(key = "'template'")
	public void deleteInBatch(Iterable<Long> ids) {
		List<RedisDBTemplate> redisTemplateList = redisDBTemplateDao.findAllById(ids);
		redisDBTemplateDao.deleteInBatch(redisTemplateList);
	}
	
	@Cacheable(key = "'template'")
	public List<RedisDBTemplate> findAllRedisDB() {
		List<RedisDBTemplate> redisTemplates = redisDBTemplateDao.findAll();
		return redisTemplates;
	}
	
	public Map<Long,RedisTemplate<String,Object>> findAll() {
		Map<Long,RedisTemplate<String,Object>> redisTemplatMap = new HashMap<Long,RedisTemplate<String,Object>>();
		List<RedisDBTemplate> redisTemplates = this.findAllRedisDB();
		log.info("Change RedisDbTemplate To Redis Cache");
		if(!CollectionUtils.isEmpty(redisTemplates)) {
			
			redisTemplates.forEach(redisDBTemplate ->{
				//redis配置
		        RedisConfiguration redisConfiguration = new 
		        							RedisStandaloneConfiguration(redisDBTemplate.getHost(),redisDBTemplate.getPort());
		        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(redisDBTemplate.getDbIndex());
		        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(redisDBTemplate.getPass());

		        //连接池配置
		        GenericObjectPoolConfig<Object> genericObjectPoolConfig =
		 												 new GenericObjectPoolConfig<Object>();
		        genericObjectPoolConfig.setMaxIdle(redisDBTemplate.getMaxIdle());
		        genericObjectPoolConfig.setMinIdle(redisDBTemplate.getMinIdle());
		        genericObjectPoolConfig.setMaxTotal(redisDBTemplate.getMaxActive());
		        genericObjectPoolConfig.setMaxWaitMillis(redisDBTemplate.getMaxWait());

		        //redis客户端配置
		        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder 
		    	  		  builder =  LettucePoolingClientConfiguration.builder().
		         				 commandTimeout(Duration.ofMillis(redisDBTemplate.getTimeOut()));
		                
		        builder.shutdownTimeout(Duration.ofMillis(redisDBTemplate.getShutdownTimeOut()));
		        builder.poolConfig(genericObjectPoolConfig);
		        LettuceClientConfiguration lettuceClientConfiguration = builder.build();

		        //根据配置和客户端配置创建连接
		        LettuceConnectionFactory lettuceConnectionFactory = new 
		        LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
		        lettuceConnectionFactory .afterPropertiesSet();
		        
		        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
				redisTemplate.setConnectionFactory(lettuceConnectionFactory);
				redisTemplate.setKeySerializer(new StringRedisSerializer());
				redisTemplate.setValueSerializer(RedisTemplateConfig.getJackson2JsonRedisSerializer());
				redisTemplate.setHashKeySerializer(new StringRedisSerializer());
				redisTemplate.setHashValueSerializer(RedisTemplateConfig.getJackson2JsonRedisSerializer());
				redisTemplate.afterPropertiesSet();
				redisTemplate.setConnectionFactory(lettuceConnectionFactory);
			    redisTemplatMap.put(redisDBTemplate.getId(), redisTemplate);
				
			});
			
		}
		return redisTemplatMap;
	}
}
