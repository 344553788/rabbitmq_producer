package cn.demo.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.demo.constant.MessageConstant;
import cn.demo.dao.DeptDao;
import cn.demo.entity.Dept;
import lombok.extern.slf4j.Slf4j;

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
			// 如果没有创建交换机的话，在投递消息的时候会投递失败
			// 第一个参数，交换机的名称
			// 第二个参数，路由key值
			// 第三个参数，消息主题对应的类
			// 第四个参数，消息的附件消息
			try {
				Dept entity = deptDao.save(action);
				lists.add(entity);
				CorrelationData correlationData = new CorrelationData();
				correlationData.setId(String.valueOf(action.getId()));
				rabbitOperations.convertAndSend(MessageConstant.ExchangeName.DIRECT_EXCHANGE,
						MessageConstant.RouteName.DIRECT_ROUTE, objectMapper.writeValueAsString(action),
						correlationData);
			} catch (Exception e) {
				log.info("error", e);
			}

		});
		return lists;
	}

}
