package cz.muni.fi.jboss.book.ejb.manager;

import java.util.List;

import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;

public interface BookManager {

	public Book addBook(Book book);
	public Book updateBook(Book book);
	public void deleteBook(Long bookId);
	
	public Book findBookById(Long id);
	public List<Book> findBookByISBN(Long ISBN);
	public List<Book> findBookByTitle(String title);
	public List<Book> findBookByTitlePart(String titlePart);
	public List<Book> findBookByAuthor(String firstName, String surname);
	public List<Book> findAllBooks();
	
	public BookCopy addBookCopy(Long bookId, BookCopy bookCopy);
	public void deleteBookCopy(Long bookCopyId);
	public BookCopy updateBookCopy(BookCopy bookCopy);

}