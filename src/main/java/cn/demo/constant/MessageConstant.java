package cn.demo.constant;

public interface MessageConstant {

	interface QueueName{

		String FANOUT_QUEUE = "FANOUT_QUEUE";
		String message_queue  = "message_queue";
		String mail_queue = "mail_queue";
		String message_fail = "message_fail";
		String dead_letter_queue ="dead_letter_queue";
	}

	interface ExchangeName{
		String mail_exchange = "mail_exchange";
		String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
		String message_exchange  = "message_exchange";
		String dead_letter_exchange ="dead_letter_exchange";
	}

	interface RouteName{
		String FANOUT_ROUTE = "FANOUT_ROUTE";
		String message_route  = "#.message";
		String message_fail ="message_fail";
		String mail_queue_fail ="mail_queue_fail";
		String mail_routing_key = "mail_routing_key";
	}

}
