package cn.demo.constant;

public interface MessageConstant {
	
	//定义消息队列,durable:是否持久化，如果想在RabbitMQ退出或崩溃的时候，不会失去所有的queue和消息，需要同时标志队列(queue)和交换机(exchange)是持久化的，
	// 即rabbit:queue标签和rabbit:direct-exchange中的durable=true,而消息(message)默认是持久化的可以看类org.springframework.amqp.core.MessageProperties中的属性
	// public static final MessageDeliveryMode DEFAULT_DELIVERY_MODE = MessageDeliveryMode.PERSISTENT;exclusive:
	//仅创建者可以使用的私有队列，断开后自动删除；auto_delete: 当所有消费客户端连接断开后，是否自动删除队列
	
	public static interface QueueName{
		public static String DIRECT_QUEUE = "DIRECT_QUEUE";
		public static String FANOUT_QUEUE = "FANOUT_QUEUE";
		public static String TOPIC_QUEUE  = "TOPIC_QUEUE";
		public static String DELAY_QUEUE ="DLX_QUEUE";
	}
	
	public static interface ExchangeName{
		public static String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";
		public static String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
		public static String TOPIC_EXCHANGE  = "TOPIC_EXCHANGE";
		public static String DELAY_EXCHANGE ="DLX_EXCHANGE";
	}
	
	public static interface RouteName{
		public static String DIRECT_ROUTE = "DIRECT_ROUTE";
		public static String FANOUT_ROUTE = "FANOUT_ROUTE";
		public static String TOPIC_ROUTE  = "#.ROUTE";
		public static String DELAY_ROUTE ="ROUTE.DLX";
	}

}
