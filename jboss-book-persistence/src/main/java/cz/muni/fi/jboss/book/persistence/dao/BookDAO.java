package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import cz.muni.fi.library.entity.Book;

/**
 * Interface BookDAO
 *
 * @author Eduard Tomek
 *
 */
public interface BookDAO {
	
    /**
     * Create book in database
     * 
     * @param book book to be created
     * @return created book
     * @throws NullPointerException if book is null
     * @throws IllegalArgumentException if pages or ISBN is null or negative
     */
    public Book createBook(Book book);
    
    
    /**
     * Update book in database
     * 
     * @param book book to be updated
     * @return updated book
     * @throws NullPointerException if book is null
     * @throws IllegalArgumentException if pages or ISBN is null or negative
     */
    public Book updateBook(Book book);

    /**
     * Delete book from database
     * 
     * @param book book to delete
     * @throws NullPointerException if book is null
     */
    public void deleteBook(Book book);
    
   /**
     * Find book by id
     * 
     * @param id id of the book
     * @return book with given id or null if no such book found
     * @throws NullPointerException if id is null
     */
    public Book findBookById(Long id);

    /**
     * Find book by ISBN
     * 
     * @param ISBN ISBN of book which will be found
     * @return book with given ISBN or null if no such book found
     * @throws NullPointerException if id is null
     */
    public List<Book> findBookByISBN(Long ISBN);

   /**
     * Find book by ISBN
     * 
     * @param title title of book which will be searched for
     * @return List of books with given name
     * @throws NullPointerException if title is null
     */
    public List<Book> findBookByTitle(String title);
	
    /**
     * Find book by author
     * 
     * @param author author of book
     * @return List of books written by given author
     * @throws NullPointerException if author is null
     */
    public List<Book> findBookByAuthor(String author);
	
    /**
     * Find all books
     * 
     * @return List of all books
     */
    public List<Book> findAllBooks();
	
}
