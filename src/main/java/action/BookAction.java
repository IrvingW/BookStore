package action;

import java.io.File;
import java.io.StringWriter;
import java.security.AccessControlException;
import java.security.PrivilegedAction;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

import org.springframework.transaction.support.ResourceTransactionManager;

import com.mysql.jdbc.Field;

import jaas.BookViewAction;
import model.Book;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import service.AppService;
import service.SecurityService;


public class BookAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AppService appService;
	private SecurityService securityService;
	
	private int id;
	private String name;
	private String author;
	private double price;
	private int stock;
	private String category;
	
	private File file;
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setFile(File file){
		this.file = file;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
		
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id_str){
		try{
			this.id = Integer.valueOf(id_str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String execute() throws Exception{
		List<Book> books = appService.getAllBooks();
		request().setAttribute("books", books);

		return SUCCESS;
	}
	
	public String delete() throws Exception{
		Book book = appService.getBookById(id);
		appService.deleteBook(book);
		List<Book> books = appService.getAllBooks();
		request().setAttribute("books", books);
		return SUCCESS;
	}
	
	public String add() throws Exception{
		Book book = new Book();
		book.setBook_name(name);
		book.setAuthor(author);
		book.setPrice(price);
		book.setStock(stock);
		book.setCategory(category);
		appService.addBook(book);
		if(file != null){
			String contentType = file.getName().split(".",1).toString();
			appService.saveFile(file, name, contentType);
		}
		List <Book> books = appService.getAllBooks();
		request().setAttribute("books", books);
		return SUCCESS;
	}
	
	public String update() throws Exception{
		Book book = appService.getBookById(id);
		book.setBook_name(name);
		book.setAuthor(author);
		book.setPrice(price);
		book.setStock(stock);
		book.setCategory(category);
		appService.updateBook(book);
		
		List <Book> books = appService.getAllBooks();
		request().setAttribute("books", books);
		
		return SUCCESS;
	}
	
	public String hint() throws Exception{
		List<String> names = appService.getHint(name);
		String result = "";
		for(String name : names){
			result += name;
			result += ";";
		}
			
		response().setContentType("text/html;charset=utf-8"); // this must before getWriter() 
		response().getWriter().print(result);
		return null;
	}
	
	public String show_detail() throws Exception{
		JsonObject model = appService.show_detail(id);
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.writeObject(model);
		}
		String jsonStr = stringWriter.toString();
		response().setContentType("text/html;charset=utf-8"); // this must before getWriter() 
		response().getWriter().write(jsonStr);
		
		return null;
	}
	
	
	public String search(){
		Jedis jedis = new Jedis("localhost");
		System.out.println("连接成功");
		String cache = jedis.get(name);
		List<Book> list;
		if(cache == null) {
			System.out.println("缓存未命中");
			list = appService.search(name);
			JSONArray json = JSONArray.fromObject(list);
			jedis.set(name, json.toString());
		}else {
			System.out.println("缓存命中");
			JSONArray json = new JSONArray().fromObject(cache);
			list = (List<Book>) JSONArray.toList(json, Book.class);
		}
		request().setAttribute("books", list);
		return "search";
	}

	public String getCategoryBook() {
		// vip-book
		if(category.equals("vip-book")) {
			String login = (String) session().getAttribute("user_name");
			if(login==null)
				return "category-denied";
			
			LoginContext context = (LoginContext) session().getAttribute("loginContext");
			if(context == null)
				return "category-denied";
			
			Subject subject = context.getSubject();
			PrivilegedAction<String> action = new BookViewAction(securityService);
			String result = Subject.doAsPrivileged(subject, action, null);
			if(result.equals("denied"))
				return "category-denied";
		}
		else {
			String view = category;
			try {
				securityService.checkPermission(view);
			}catch (AccessControlException e) {
				return "category-denied";
			}
		}
		
		List<Book> books =appService.getBookByCategory(category);
		request().setAttribute("books", books);
		return "category";
	}
	
}
