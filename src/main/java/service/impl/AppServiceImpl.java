package service.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.query.OrdinalParameterDescriptor;

import com.mongodb.gridfs.GridFSDBFile;

import model.Book;
import model.Cart_item;
import model.Order;
import model.Orderitem;
import model.Statistic;
import model.User;
import service.AppService;
import dao.BookDao;
import dao.FileDao;
import dao.OrderDao;
import dao.OrderitemDao;
import dao.UserDao;


public class AppServiceImpl implements AppService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private OrderitemDao orderitemDao;
	private UserDao userDao;
	private FileDao fileDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setOrderitemDao(OrderitemDao orderitemDao) {
		this.orderitemDao = orderitemDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setFileDao(FileDao fileDao){
		this.fileDao = fileDao;
	}

	/**
	 * book
	 * 
	 */
	public Integer addBook(Book book) {
		return bookDao.save(book);
	}

	public void deleteBook(Book book) {
		bookDao.delete(book);
	}

	public void updateBook(Book book) {
		bookDao.update(book);
	}

	public Book getBookById(int id) {
		return bookDao.getBookById(id);
	}
	
	public Book getBookByName(String name){
		return bookDao.getBookByName(name);
	}

	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}
	
	public List<String> getHint(String str){
		return bookDao.getHint(str);
	}
	
	public List<Book> search(String str){
		return bookDao.search(str);
	}
	
	public JsonObject show_detail(int id){
		Book book = getBookById(id);
		String name= book.getBook_name();
		String author = book.getAuthor();
		int stock = book.getStock();
		double price = book.getPrice();
		JsonObject model = Json.createObjectBuilder()
				.add("name", name)
				.add("author", author)
				.add("price", price)
				.add("stock", stock)
				.build();
		
		return model;
	}

	/**
	 * order
	 * 
	 */
	public Order addOrder(Order order) {
		return orderDao.save(order);
	}

	public void deleteOrder(Order order) {
		Set<Orderitem> orderitems = order.getOrderitems();
		for(Orderitem orderitem : orderitems)
			deleteOrderitem(orderitem);
		orderDao.delete(order);
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public Order getOrderById(int id) {
		return orderDao.getOrderById(id);
	}

	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}
	
	public List<Order> getOrderByUserId(int user_id){
		return orderDao.getOrderByUserId(user_id);
	}
	
	public List<Orderitem> getItems(int order_id){
		return orderDao.getOrderitemByOrderId(order_id);
	}

	/**
	 * order item
	 * 
	 */
	public Integer addOrderitem(Orderitem orderitem) {
		Book book = bookDao.getBookById(orderitem.getBook_id());
		book.setStock(book.getStock() - orderitem.getAmount());
		bookDao.update(book);
		return orderitemDao.save(orderitem);
	}

	public void deleteOrderitem(Orderitem orderitem) {
		Book book = bookDao.getBookById(orderitem.getBook_id());
		book.setStock(book.getStock() + orderitem.getAmount());
		bookDao.update(book);
		orderitemDao.delete(orderitem);
	}

	public void updateOrderitem(Orderitem orderitem) {
		orderitemDao.update(orderitem);
	}

	public Orderitem getOrderitemById(int id) {
		return orderitemDao.getOrderitemById(id);
	}

	public List<Orderitem> getAllOrderitems() {
		return orderitemDao.getAllOrderitems();
	}

	/**
	 * user
	 * 
	 */
	public Integer addUser(User user) {
		return userDao.save(user);
	}

	public void deleteUser(User user) {
		userDao.delete(user);
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	
	public User getUserByName(String name){
		return userDao.getUserByName(name);
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public String getIntroByName(String name){
		return userDao.getIntroByName(name);
	}
	
	public void saveProfile(String name, String introduce){
		userDao.saveProfile(name, introduce);
	}

	public JsonObject profile(String user_name){
		User user = this.getUserByName(user_name);
		String u_intro = this.getIntroByName(user_name);
		String u_name = user.getUser_name();
		String u_pwd = user.getPassword();
		String u_phone = user.getPhone();
		String u_email = user.getEmail();
		String u_addr = user.getAddress();
		JsonObject model = Json.createObjectBuilder()
				.add("name", u_name)
				.add("pwd", u_pwd)
				.add("phone", u_phone)
				.add("email", u_email)
				.add("addr",u_addr)
				.add("intro", u_intro)
				.build();
		return model;
	}
	
	public void check_pwd(PrintWriter out, String name, String password){
		if(name==null){
			out.write("login");
			return;
		}
		User user = getUserByName(name);
		String pre_pwd = user.getPassword();
		
		if(pre_pwd.equals(password)){
			out.write("right");
		}
		else{
			out.write("wrong");
		}
	}
	
	/*
	 * File
	 */
	 
	public void saveFile(File file, String fileName, String contentType){
		fileDao.saveFile(file, fileName, contentType);
	}

	public GridFSDBFile getFile(String fileName){
		return fileDao.getFile(fileName);
	}
	
	/*
	 * 
	 * Statistic
	 * 
	 */
	
	public List<Statistic> getUserStatistics(String user_name, Date start_time, Date end_time){
		List<Statistic> statistics = new ArrayList<>();
		
		Calendar start = Calendar.getInstance();
		start.setTime(start_time);
		Calendar end = Calendar.getInstance();
		end.setTime(end_time);
		Calendar temp = (Calendar) start.clone();
		while(!temp.after(end)){
			Statistic statistic = new Statistic();
			List<Order> orders = orderDao.getOrderByDate(temp.getTime());
			int count = 0;
			for(Order order : orders){
				if(order.getUser_id() == userDao.getUserByName(user_name).getId())
					for(Orderitem orderitem :order.getOrderitems()){
						count += orderitem.getAmount();
					}
			}
			statistic.setDate(temp.getTime());;
			statistic.setCount(count);
			statistics.add(statistic);
			temp.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return statistics;
	}
	
	public List<Statistic> getBookStatistics(String book_name, Date start_time, Date end_time){
		List<Statistic> statistics = new ArrayList<>();
		
		Calendar start = Calendar.getInstance();
		start.setTime(start_time);
		Calendar end = Calendar.getInstance();
		end.setTime(end_time);
		Calendar temp = (Calendar) start.clone();
		while(!temp.after(end)){
			Statistic statistic = new Statistic();
			List<Order> orders = orderDao.getOrderByDate(temp.getTime());
			int count = 0;
			for(Order order : orders){
				for(Orderitem orderitem : order.getOrderitems()){
					int book_id = orderitem.getBook_id();
					if(bookDao.getBookById(book_id).getBook_name().equals(book_name)){
						count += orderitem.getAmount();
					}
				}
			}
			statistic.setDate(temp.getTime());;
			statistic.setCount(count);
			statistics.add(statistic);
			temp.add(Calendar.DAY_OF_YEAR, 1);
		}
		return statistics;
	}
	
	public List<Statistic> getCategoryStatistics(String category, Date start_time, Date end_time){
		List<Statistic> statistics = new ArrayList<>();
		
		Calendar start = Calendar.getInstance();
		start.setTime(start_time);
		Calendar end = Calendar.getInstance();
		end.setTime(end_time);
		Calendar temp = (Calendar) start.clone();
		while(!temp.after(end)){
			Statistic statistic = new Statistic();
			List<Order> orders = orderDao.getOrderByDate(temp.getTime());
			int count = 0;
			for(Order order : orders){
				for(Orderitem orderitem : order.getOrderitems()){
					int book_id = orderitem.getBook_id();
					if(bookDao.getBookById(book_id).getCategory().equals(category)){
						count += orderitem.getAmount();
					}
				}
			}
			statistic.setDate(temp.getTime());;
			statistic.setCount(count);
			statistics.add(statistic);
			temp.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return statistics;
	}
	
	
	/*
	 * 
	 * cart
	 * 
	 */
	
	public void add_product(PrintWriter out, HttpSession session, int book_id){
		@SuppressWarnings("unchecked")
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
		if(cart==null){
			out.write("login");
			return;
		}
		if(bookDao.getBookById(book_id).getStock() == 0){
			out.write("not_enough");
			return;
		}
		if(cart.containsKey(book_id)){
			cart.put(book_id, (cart.get(book_id)+1));
		}else{
			cart.put(book_id, 1);
		}
		session.setAttribute("cart", cart);
		out.write("success");
	}
	 
	public void rmv_product(HttpSession session, int book_id){
		@SuppressWarnings("unchecked")
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
		cart.remove(book_id);
		session.setAttribute("cart", cart);
	}
	
	 public void update_cnt(HttpSession session, int book_id, String method){
			@SuppressWarnings("unchecked")
			HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
			int old_cnt = cart.get(book_id);
			if(method.equals("add"))
				cart.put(book_id, (old_cnt+1));
			if(method.equals("minus"))
				if(old_cnt == 1) // if new count = 0, remove it form cart
					cart.remove(book_id);
				else 
					cart.put(book_id, (old_cnt-1));
			
			session.setAttribute("cart", cart);
	 }
	 
	 public void pay(HttpSession session, HttpServletRequest request){
		 @SuppressWarnings("unchecked")
			HashMap<Integer, Integer> map = (HashMap<Integer, Integer>)session.getAttribute("cart");
			List<Cart_item> cart = new ArrayList<Cart_item>();
			for(Map.Entry<Integer,Integer> entry: map.entrySet()){
				int e_id = entry.getKey();
				int e_count = entry.getValue();
				Book book = this.getBookById(e_id);
				//cart.add(new Cart_item(book.getBook_name(), e_count, book.getPrice()));
				Cart_item item = new Cart_item();
				item.setBook_id(e_id);
				item.setBook_name(book.getBook_name());
				item.setCount(e_count);
				item.setPrice(book.getPrice());
				cart.add(item);
			}
			request.setAttribute("cart", cart);
	 }
	 
	 
	 public void make_order(HttpSession session, String selected_id){
			User user = this.getUserByName((String) session.getAttribute("user_name"));
			int user_id = user.getId();
			Order order = new Order();
			order.setUser_id(user_id);
			//get system time
			Date date=new Date(System.currentTimeMillis());		
			order.setDate(date);
			order.setState("unpaid");
			order = this.addOrder(order);
					
			Set<Orderitem> items = new HashSet<Orderitem>();
			@SuppressWarnings("unchecked")
			HashMap<Integer, Integer> map = (HashMap<Integer, Integer>)session.getAttribute("cart");  
			Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();  
			while(it.hasNext()){  
				Map.Entry<Integer, Integer> entry=it.next(); 
		        int book_id = entry.getKey();
		        if(selected_id.contains(String.valueOf(book_id)) == true){
				Orderitem item = new Orderitem();
				Book book = this.getBookById(book_id);
				double price = book.getPrice();
				item.setOrder(order);
				item.setBook_id(book_id);
				item.setEach_price(price);
				item.setAmount(entry.getValue());
				items.add(item);
							
				this.addOrderitem(item);
				it.remove();
				}
			}  
			order.setOrderitems(items);
			this.updateOrder(order);
	 }
	
}
