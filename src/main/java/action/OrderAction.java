package action;

import java.util.List;

import model.Order;
import service.AppService;

public class OrderAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private AppService appService;
	private int id;
	private int user_id;
	private int book_id;
	private String time;


	public void setAppService(AppService appService) {
		this.appService = appService;
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

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String execute() throws Exception{
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);

		return SUCCESS;
	}
	
	public String delete() throws Exception{
		Order order = appService.getOrderById(id);
		appService.deleteOrder(order);
		
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);
		return SUCCESS;
	}
	
	public String add() throws Exception{
		Order order = new Order();
		order.setBook_id(book_id);
		order.setUser_id(user_id);
		order.setTime(time);
		appService.addOrder(order);
		
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);
		return SUCCESS;
	}
	
	public String update() throws Exception{
		Order order = appService.getOrderById(id);
		order.setBook_id(book_id);
		order.setUser_id(user_id);
		order.setTime(time);
		appService.updateOrder(order);
		
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);
		return SUCCESS;
	}
}
