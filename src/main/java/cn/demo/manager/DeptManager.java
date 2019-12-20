package cn.demo.manager;

import cn.demo.constant.MessageConstant;
import cn.demo.dao.DeptDao;
import cn.demo.entity.Dept;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class DeptManager {

	@Autowired
	private DeptDao deptDao;
	@Autowired
	private RabbitOperations rabbitOperations;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional(rollbackFor = Exception.class)
	public List<Dept> saveAll(Iterable<Dept> entities) {
		List<Dept> lists = new ArrayList<Dept>();
		entities.forEach(action -> {
			Dept entity = deptDao.save(action);
			lists.add(entity);
			CorrelationData correlationData =new CorrelationData(UUID.randomUUID().toString());
			try {
				rabbitOperations.convertAndSend(MessageConstant.ExchangeName.message_exchange,
						MessageConstant.RouteName.message_route.replace("#",String.valueOf(entity.getId())), objectMapper.writeValueAsString(action),
						correlationData);
			} catch (IOException e) {
				log.info("error", e);
			}

		});
		return lists;
	}

}
