package dao.impl;

import java.util.List;

import model.Book;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;

@Transactional(isolation=Isolation.READ_COMMITTED)
public class BookDaoImpl extends HibernateDaoSupport implements BookDao {

	public Integer save(Book book) {
		return (Integer) getHibernateTemplate().save(book);
	}

	public void delete(Book book) {
		getHibernateTemplate().delete(book);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public void update(Book book) {
		getHibernateTemplate().merge(book);
	}

	public Book getBookById(int id) {
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) getHibernateTemplate().find(
				"from Book as b where b.id=?", id);
		Book book = books.size() > 0 ? books.get(0) : null;
		return book;
	}
	
	public Book getBookByName(String name){
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) getHibernateTemplate().find(
				"from Book as b where b.book_name=?", name);
		if(books.size() == 0)
			return null;
		return books.get(0);
	}

	public List<Book> getAllBooks() {
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) getHibernateTemplate()
				.find("from Book");
		return books;
	}
	
	public List<String> getHint(String str){
		@SuppressWarnings("unchecked")
		List<String> book_names = (List<String>) getHibernateTemplate().find(
				"select b.book_name from Book as b where b.book_name like '%" + str + "%'");
		return  book_names;
	}
	
	public List<Book> search(String str){
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) getHibernateTemplate()
			.find("select b from Book as b where b.book_name like '%" + str + "%'");
		return books;
	}
	
	public List<Book> getBookByCategory(String category){
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) getHibernateTemplate()
			.find("select b from Book as b where b.category=?", category);
		return books;
	}

}
