package cn.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void init() {
		rabbitTemplate.setConfirmCallback(this); // 指定 ConfirmCallback
		rabbitTemplate.setReturnCallback(this);
	}

	// 创建一个消息是否投递成功的监听对象confirmCallback,发送到交换机
	// 第一个参数， 消息的附加消息(自定义id)，
	// 第二个参数， 消息是否被Broker接收，isAck(true接收,false拒收)，
	// 第三个参数， 如果拒收，则返回拒收的原因
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = RuntimeException.class)
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("======》correlationData:{} ", correlationData);
		log.info("======》isAck: {}", ack);
        if(!ack){
        	/**
        	 * 此处应该搞个计数。超过3次就不发了，上定时任务
        	 */
        	Message message = correlationData.getReturnedMessage();
        	MessageProperties messageProperties =correlationData.getReturnedMessage().getMessageProperties();
        	log.error("消费发送失败异常处理");
        	rabbitTemplate.convertAndSend(messageProperties.getReceivedExchange(), messageProperties.getReceivedRoutingKey(), message,correlationData);
        	throw new RuntimeException(cause);
        }
	}
	
	// 创建一个处理消息是否被队列接收的监听对象，如果没有队列接收发出的消息，则会自动调用returnedMessage方法，进行后续的处理
	// 第一个参数， 被退回的消息
	// 第二个参数， 错误编码
	// 第三个参数， 错误描述
	// 第四个参数， 交换机的名字
	// 第五个参数， 路由Key
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchangeName,
			String routingKey) {
		System.err.println("-----returnedMessage++code: " + replyCode + ",  Text: " + replyText);
		System.err.println("-----returnedMessage++exchangeName: " + exchangeName + ",  routingKey: " + routingKey);
		System.err.println("-----returnedMessage++message: " + message);
	}
}
