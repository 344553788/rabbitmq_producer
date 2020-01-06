package cn.demo.constant;

public interface MessageConstant {
	
	public static interface Queue{
		public static String DIRECT_QUEUE = "direct.queue";
		public static String FANOUT_QUEUE = "fanout.queue";
		public static String TOPIC_QUEUE  = "topic.queue";
		public static String DLX_QUEUE ="dlx_queue";
	}
	
	public static interface Exchange{
		public static String DIRECT_EXCHANGE = "direct.exc";
		public static String FANOUT_EXCHANGE = "fanout.exc";
		public static String TOPIC_EXCHANGE  = "topic.exc";
		public static String DLX_EXCHANGE ="dlx_exc";
	}
	
	public static interface Route{
		public static String DIRECT_ROUTE = "direct.route";
		public static String FANOUT_ROUTE = "fanout.route";
		public static String TOPIC_ROUTE  = "#.route";
		public static String DLX_ROUTE ="dlx.route";
	}
}
