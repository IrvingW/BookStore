package dao.impl;

import java.util.List;

import model.Book;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.BookDao;

public class BookDaoImpl extends HibernateDaoSupport implements BookDao {

	public Integer save(Book book) {
		return (Integer) getHibernateTemplate().save(book);
	}

	public void delete(Book book) {
		getHibernateTemplate().delete(book);
	}

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

}
