package cn.demo.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {
	@Override
	public void onApplicationEvent(ApplicationFailedEvent event) {
		ConfigurableApplicationContext applicationContext = event.getApplicationContext();
		// 如果不是 dev 环境,因为 dev 环境会查看控制台
		if (applicationContext == null || applicationContext.getEnvironment().acceptsProfiles(Profiles.of("!dev"))) {
			// 进行异常通知
			log.error("系统启动失败......");
		}
	}
}
