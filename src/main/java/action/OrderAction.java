package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Book;
import model.Order;
import model.Orderitem;
import model.User;
import service.AppService;

public class OrderAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private AppService appService;
	private int id;
	private int user_id;
	private Date date;
	private String state;


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




	@Override
	public String execute() throws Exception{
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);
		
		List<User> users = appService.getAllUsers();
		List<Integer> user_list = new ArrayList<Integer>();
		for(User user : users){
			user_list.add(user.getId());
		}

		request().setAttribute("user_list", user_list);
		

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
		order.setUser_id(user_id);
		order.setDate(date);
		order.setState(state);
		appService.addOrder(order);
		
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);
		return SUCCESS;
	}
	
	public String update() throws Exception{
		Order order = appService.getOrderById(id);
		order.setUser_id(user_id);
		order.setDate(date);
		order.setState(state);
		appService.updateOrder(order);
		
		List<Order> orders = appService.getAllOrders();
		request().setAttribute("orders", orders);
		return SUCCESS;
	}
	
	public String getOrders() throws Exception{
		User user = appService.getUserByName((String)session().getAttribute("user_name"));
		user_id = user.getId();
		List<Order>orders = appService.getOrderByUserId(user_id);
		request().setAttribute("orders", orders);
		return "orders";
	}
	
	public String getItems() throws Exception{
		List<Orderitem> orderitems = appService.getItems(id);
		request().setAttribute("orderitems", orderitems);
		return "items";
	}
}
