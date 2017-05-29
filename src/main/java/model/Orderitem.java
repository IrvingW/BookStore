package model;

public class Orderitem {

	private int id;

	private int book_id;
	private int amount;
	private double each_price;
	
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getEach_price() {
		return each_price;
	}
	public void setEach_price(double each_price) {
		this.each_price = each_price;
	}

	
	

}
