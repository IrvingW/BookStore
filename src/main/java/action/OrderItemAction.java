package action;

import java.util.Date;
import java.util.List;

import model.Book;
import model.Orderitem;
import service.AppService;

public class OrderItemAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private AppService appService;
	private int id;
	private int order_id;
	private int book_id;
	private int amount;
	private double each_price;
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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
	
	@Override
	public String execute() throws Exception{
		return SUCCESS;
	}
	
	public String update() throws Exception{
		Orderitem orderitem = appService.getOrderitemById(id);
		orderitem.setAmount(amount);
		orderitem.setBook_id(book_id);
		orderitem.setEach_price(each_price);
		Book book = appService.getBookById(id);
		appService.updateOrderitem(orderitem);
		
		List<Orderitem> orderitems = appService.getItems(order_id);
		request().setAttribute("orderitems", orderitems);
		
		return SUCCESS;
	}
	
	public String delete() throws Exception{
		Orderitem orderitem = appService.getOrderitemById(id);
		appService.deleteOrderitem(orderitem);

		List<Orderitem> orderitems = appService.getItems(order_id);
		request().setAttribute("orderitems", orderitems);
		
		return SUCCESS;
	}
	
}
