package service.impl;

import java.awt.FileDialog;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mongodb.gridfs.GridFSDBFile;

import model.Book;
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

	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}
	
	public List<String> getHint(String str){
		return bookDao.getHint(str);
	}
	
	public List<Book> search(String str){
		return bookDao.search(str);
	}

	/**
	 * order
	 * 
	 */
	public Order addOrder(Order order) {
		return orderDao.save(order);
	}

	public void deleteOrder(Order order) {
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
		return orderitemDao.save(orderitem);
	}

	public void deleteOrderitem(Orderitem orderitem) {
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
	
	
}
