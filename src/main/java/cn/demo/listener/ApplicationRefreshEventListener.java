package cn.demo.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.demo.dao.RedisDBTemplateDao;
import cn.demo.entity.RedisDBTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("!prod")
public class ApplicationRefreshEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private RedisDBTemplateDao redisDBTemplateDao;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(event.getApplicationContext().getParent() == null) {
			log.info("register computer to zooker");
			RedisDBTemplate redisDBTemplate = RedisDBTemplate.builder()
					.host("192.168.1.19").dbIndex(6).maxActive(8).maxIdle(8).minIdle(8)
					.port(6379).maxActive(8).shutdownTimeOut(10000l).timeOut(6000l).maxWait(-1l).build();
			redisDBTemplateDao.save(redisDBTemplate);
			
			RedisDBTemplate localRedisDBTemplate = RedisDBTemplate.builder()
					.host("localhost").dbIndex(6).maxActive(8).maxIdle(8).minIdle(8)
					.port(6379).maxActive(8).shutdownTimeOut(10000l).timeOut(6000l).maxWait(-1l).build();
			redisDBTemplateDao.save(localRedisDBTemplate);
			
			
			RedisDBTemplate preRedisDBTemplate = RedisDBTemplate.builder().pass("Face6ook20-17")
					.host("192.168.0.45").dbIndex(6).maxActive(8).maxIdle(8).minIdle(8)
					.port(6379).maxActive(8).shutdownTimeOut(10000l).timeOut(6000l).maxWait(-1l).build();
			redisDBTemplateDao.save(preRedisDBTemplate);
		}
	}
	

}
