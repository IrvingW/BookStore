package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {
	private int id;
	private int user_id;
	private Date date;
	private String state;
	
	private Set<Orderitem> orderitems = new HashSet<Orderitem>();

	public Set<Orderitem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(Set<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public double getTotal(){
		double total = 0;
		for(Orderitem orderitem :orderitems){
			total += orderitem.getPrice();
		}
		return total;
	}
	
	
	
}
