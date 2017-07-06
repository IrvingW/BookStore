package service;

import java.io.File;
import java.util.List;

import com.mongodb.gridfs.GridFSDBFile;

import model.Book;
import model.Order;
import model.Orderitem;
import model.User;

/**
 * @author seniyuting
 * @version 1.0
 * 
 */
public interface AppService {

	/**
	 * book
	 * 
	 */
	public Integer addBook(Book book);

	public void deleteBook(Book book);

	public void updateBook(Book book);

	public Book getBookById(int id);

	public List<Book> getAllBooks();
	
	public List<String> getHint(String str);

	/**
	 * order
	 * 
	 */
	public Order addOrder(Order order);

	public void deleteOrder(Order order);

	public void updateOrder(Order order);

	public Order getOrderById(int id);

	public List<Order> getAllOrders();
	
	public List<Orderitem> getItems(int order_id);

	/**
	 * order item
	 * 
	 */
	public Integer addOrderitem(Orderitem orderitem);

	public void deleteOrderitem(Orderitem orderitem);

	public void updateOrderitem(Orderitem orderitem);

	public Orderitem getOrderitemById(int id);

	public List<Orderitem> getAllOrderitems();

	/**
	 * user
	 * 
	 */
	public Integer addUser(User user);

	public void deleteUser(User user);

	public void updateUser(User user);

	public User getUserById(int id);
	
	public User getUserByName(String name);

	public List<User> getAllUsers();
	
	public List<Order> getOrderByUserId(int user_id);
	
	
	/**
	 * 
	 * file
	 * 
	 */
	
	 public void saveFile(File file, String fileName, String contentType);
	 
	 public GridFSDBFile getFile(String fileNaem);

}
