package action;

import java.io.StringWriter;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import model.Book;
import service.AppService;

public class BookAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AppService appService;
	
	private int id;
	private String name;
	private String author;
	private double price;
	private int stock;
	
	
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
		appService.addBook(book);
		
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
			
		response().getWriter().print(result);
		return null;
	}
	
	public String show_detail() throws Exception{
		Book book = appService.getBookById(id);
		name= book.getBook_name();
		author = book.getAuthor();
		stock = book.getStock();
		price = book.getPrice();
		JsonObject model = Json.createObjectBuilder()
				.add("name", name)
				.add("author", author)
				.add("price", price)
				.add("stock", stock)				
				.build();
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.writeObject(model);
		}
		String jsonStr = stringWriter.toString();
		response().getWriter().write(jsonStr);
		
		return null;
	}
}
