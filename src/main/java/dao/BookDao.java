package dao;

import java.util.List;

import model.Book;

public interface BookDao {

	public Integer save(Book book);

	public void delete(Book book);

	public void update(Book book);

	public Book getBookById(int id);

	public Book getBookByName(String name);
	
	public List<Book> getAllBooks();
	
	public List<String> getHint(String str);
	
	public List<Book> search(String str);
	
	public List<Book> getBookByCategory(String category);
}