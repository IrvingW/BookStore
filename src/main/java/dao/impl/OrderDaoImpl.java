package dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Order;
import model.Orderitem;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.OrderDao;

public class OrderDaoImpl extends HibernateDaoSupport implements OrderDao {

	public Order save(Order order) {
		int test = (Integer)getHibernateTemplate().save(order);
		getHibernateTemplate().flush();
		return order;
	}

	public void delete(Order order) {
		getHibernateTemplate().delete(order);
	}

	public void update(Order order) {
		getHibernateTemplate().merge(order);
	}

	public Order getOrderById(int id) {
		@SuppressWarnings("unchecked")
		List<Order> orders = (List<Order>) getHibernateTemplate().find(
				"from Order as o where o.id=?", id);
		Order order = orders.size() > 0 ? orders.get(0) : null;
		return order;
	}

	public List<Order> getAllOrders() {
		@SuppressWarnings("unchecked")
		List<Order> orders = (List<Order>) getHibernateTemplate().find(
				"from Order");
		return orders;
	}
	
	public List<Order> getOrderByUserId(int user_id){
		@SuppressWarnings("unchecked")
		List<Order> orders = (List<Order>) getHibernateTemplate().find(
				"from Order o where o.user_id = ?", user_id);
		return orders;
	}
	
	public List<Orderitem> getOrderitemByOrderId(int order_id){
		@SuppressWarnings("unchecked")
		List<Orderitem> orderitems = (List<Orderitem>) getHibernateTemplate()
				.find("from Orderitem as oi where oi.order.id = ?", order_id);
		return orderitems;
	}
	
	public List<Order> getOrderByDate(Date date){
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.add(Calendar.SECOND, 24*3600-1);
		
		@SuppressWarnings("unchecked")
		List<Order> orders = (List<Order>) getHibernateTemplate()
				.find("from Order as order where order.date between ? and ?", date, end.getTime());
		return orders;
		
	}

}
