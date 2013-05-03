package cz.muni.fi.library.ejb.manager;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.jboss.book.persistence.dao.BookDAO;
import cz.muni.fi.jboss.book.persistence.entity.Book;

@Dependent
@Named("bookManager")
@Stateless
@Remote
public class BookManagerImpl implements BookManager {
	
	@Inject
	private BookDAO bookDao;

	@Override
	public Book addBook(Book book) {
		return bookDao.createBook(book);
	}

	@Override
	public Book updateBook(Book book) {
		return bookDao.updateBook(book);
	}

	@Override
	public void deleteBook(Book book) {
		bookDao.deleteBook(book);
	}

	@Override
	public Book findBookById(Long id) {
		return bookDao.findBookById(id);
	}

	@Override
	public List<Book> findBookByISBN(Long ISBN) {
		return bookDao.findBookByISBN(ISBN);
	}

	@Override
	public List<Book> findBookByTitle(String title) {
		return bookDao.findBookByTitle(title);
	}

	@Override
	public List<Book> findBookByAuthor(String author) {
		return bookDao.findBookByAuthor(author);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookDao.findAllBooks();
	}

}
