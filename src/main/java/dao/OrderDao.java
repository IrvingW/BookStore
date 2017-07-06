package dao;

import java.util.List;

import model.Order;
import model.Orderitem;

public interface OrderDao {

	public Order save(Order order);

	public void delete(Order order);

	public void update(Order order);

	public Order getOrderById(int id);

	public List<Order> getAllOrders();
	
	public List<Order> getOrderByUserId(int user_id);
	
	public List<Orderitem> getOrderitemByOrderId(int order_id);

}