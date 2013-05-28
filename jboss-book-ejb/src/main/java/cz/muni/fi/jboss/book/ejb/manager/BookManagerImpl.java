package cz.muni.fi.jboss.book.ejb.manager;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.jboss.book.persistence.dao.BookCopyDAO;
import cz.muni.fi.jboss.book.persistence.dao.BookDAO;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;

@Dependent
@Named("bookManager")
@Stateless
@Remote
public class BookManagerImpl implements BookManager {
	
	@Inject
	private BookDAO bookDao;
	
	@Inject
	private BookCopyDAO bookCopyDao;

	@Override
	public Book addBook(Book book) {
		return bookDao.createBook(book);
	}

	@Override
	public Book updateBook(Book book) {
		return bookDao.updateBook(book);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookDao.deleteBook(bookDao.findBookById(bookId));
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
	public List<Book> findBookByAuthor(String firstName, String surname) {
		Author author = new Author();
		author.setFirstName(firstName);
		author.setSurname(surname);
		
		return bookDao.findBookByAuthor(author);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookDao.findAllBooks();
	}

	@Override
	public BookCopy addBookCopy(Long bookId, BookCopy bookCopy) {
		bookCopy.setBook(bookDao.findBookById(bookId));
		return bookCopyDao.createBookCopy(bookCopy);
	}

	@Override
	public void deleteBookCopy(Long bookCopyId) {
		bookCopyDao.deleteBookCopy(bookCopyDao.findBookCopyById(bookCopyId));
	}

	@Override
	public BookCopy updateBookCopy(BookCopy bookCopy) {
		return bookCopyDao.updateBookCopy(bookCopy);
	}
	
	

}
