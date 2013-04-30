package cz.muni.fi.jboss.book.persistence.dao;

import cz.muni.fi.library.entity.Book;
import cz.muni.fi.library.entity.BookCopy;
import java.util.List;

/**
 * Interface BookCopyDAO 
 *
 * @author Eduard Tomek
 *
 */
public interface BookCopyDAO {

    /**
     * Create Book Copy 
     * 
     * @param bookCopy book copy which will be created
     * @return created book copy 
     * @throws NullPointerException if bookCopy is null
     * @throws IllegalArgumentException if book or purchaseDate is null
     */
    BookCopy createBookCopy(BookCopy bookCopy);
	
    /**
     * Update book copy
     * 
     * @param bookCopy book copy to update
     * @return updated book copy
     * @throws NullPointerException if bookCopy is null
     * @throws IllegalArgumentException if book or purchaseDate is null
     */
    BookCopy updateBookCopy(BookCopy bookCopy);
	
    /**
     * Delete book copy
     * 
     * @param bookCopy book copy which will be deleted
     */
    void deleteBookCopy(BookCopy bookCopy);
    
    
    /**
     * Find book copy by Id
     * 
     * @param id id of book copy
     * @return book copy with given id, null if there is no such book copy
     */
    BookCopy findBookCopyById(Long id);
        
    /**
     * Find book copy by book. Finds all book copies of given book.
     * 
     * @param bookCopy
     * @return List of book copies of given book
     */
    List<BookCopy> findBookCopyByBook(Book book);
    
    /**
     * Find all book copies
     * 
     * @return all book copies
     */
    List<BookCopy> findAllBookCopies();
	
}
