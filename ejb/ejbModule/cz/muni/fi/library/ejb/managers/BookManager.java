package cz.muni.fi.library.ejb.managers;

import java.util.List;

import cz.muni.fi.jboss.book.persistence.entity.Book;

public interface BookManager {

	public Book addBook(Book book);
	public Book updateBook(Book book);
	public void deleteBook(Book book);
	
	public Book findBookById(Long id);
	public List<Book> findBookByISBN(Long ISBN);
	public List<Book> findBookByTitle(String title);
	public List<Book> findBookByAuthor(String author);
	public List<Book> findAllBooks();

}