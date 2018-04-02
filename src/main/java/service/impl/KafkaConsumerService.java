package service.impl;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;
import dao.OrderDao;
import dao.OrderitemDao;
import model.Book;
import model.Order;
import model.Orderitem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KafkaConsumerService implements MessageListener<String, String> {  
	private static OrderDao orderDao;
	private static BookDao bookDao;
	private static OrderitemDao orderitemDao;
	
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setOrderitemDao(OrderitemDao orderitemDao) {
		this.orderitemDao = orderitemDao;
	}

	public Order addOrder(Order order) {
		return orderDao.save(order);
	}
	
	public void updateOrder(Order order) {
		orderDao.update(order);
	}
	
	public Book getBookById(int id) {
		return bookDao.getBookById(id);
	}
	
	public Integer addOrderitem(Orderitem orderitem) {
		Book book = bookDao.getBookById(orderitem.getBook_id());
		int new_stock = book.getStock() - orderitem.getAmount();
		// do not have enough book
		if(new_stock < 0) throw new RuntimeException("out of stock, book id:" + book.getId() + ", book name:" + book.getBook_name());
		
		book.setStock(new_stock);
		bookDao.update(book);
		return orderitemDao.save(orderitem);
	}
	
	@Transactional
	/* 过程是先新建一个订单，插入数据库，然后添加orderItem，更新数据库，需要事务支持 */
    @Override  
    public void onMessage(ConsumerRecord<String, String> record) {  
    	System.out.println("KafkaConsumerServer=============kafkaConsumer开始消费=============");  
        String topic = record.topic();
        String key = record.key();  
        String value = record.value();  
        long offset = record.offset();  
        int partition = record.partition();  
        System.out.println("KafkaConsumerServer-------------topic:"+topic);  
        System.out.println("KafkaConsumerServer-------------value:"+value);  
        System.out.println("KafkaConsumerServer-------------key:"+key);  
        System.out.println("KafkaConsumerServer-------------offset:"+offset);  
        System.out.println("KafkaConsumerServer-------------partition:"+partition);  
        System.out.println("~~~~~~~~~~~~~kafkaConsumer消费结束~~~~~~~~~~~~~");  
        System.out.println("消费成功***************************************************************");  
        
        // deal with new order
        String json_str = record.value();
        JSONArray jsonArray = JSONArray.fromObject(json_str);
        int user_id = ((JSONObject)jsonArray.get(0)).getInt("user_id");
        String selected_id = ((JSONObject)jsonArray.get(0)).getString("selected_id");
		Order order = new Order();
		order.setUser_id(user_id);
		//get system time
		Date date=new Date(System.currentTimeMillis());		
		order.setDate(date);
		order.setState("unpaid");
		order = this.addOrder(order);
				
		Set<Orderitem> items = new HashSet<Orderitem>();
		for(int i = 1; i < jsonArray.size(); i++) {
			JSONObject book_json = jsonArray.getJSONObject(i);
			int book_id = book_json.getInt("book_id");
			int count = book_json.getInt("count");
			if(selected_id.contains(String.valueOf(book_id)) == true){
				Orderitem item = new Orderitem();
				Book book = this.getBookById(book_id);
				double price = book.getPrice();
				item.setOrder(order);
				item.setBook_id(book_id);
				item.setEach_price(price);
				item.setAmount(count);
				items.add(item);
				
				// may throw a exception for out of stock reason
				this.addOrderitem(item);
			}
		}
		order.setOrderitems(items);
		this.updateOrder(order);
    }  
    
    
}  
