package cz.muni.fi.jboss.book.persistence.dao;

import cz.muni.fi.library.entity.Librarian;
import java.util.List;

/**
 *
 * @author Eduard Tomek
 */
public interface LibrarianDAO {
    
    /**
     * Create Librarian
     * 
     * @param librarian
     * @return created librarian
     * @throws NullPointerException if librarian is null
     * @throws IllegalArgumentException if librarian with given username already exists
     */
    Librarian createLibrarian(Librarian librarian);
    
    /**
     * Update Librarian
     * 
     * @param librarian
     * @return updated librarian
     * @throws NullPointerException if librarian is null
     */
    Librarian updateLibrarian(Librarian librarian);
    
    /**
     * Delete librarian 
     * 
     * @param librarian 
     * @throws NullPointerException if librarian is null
     */
    void deleteLibrarian(Librarian librarian);
    
    /**
     * Find librarian by id
     * 
     * @param id
     * @return librarian with given id, null if such librarian doesn't exist
     * @throws NullPointerException if id is null
     */
    Librarian findLibrarianById(Long id);
    
    /**
     * Find librarian by Username
     * 
     * @param id
     * @return librarian with given username, null if librarian with given
     *      username doesn't exist
     * @throws NullPointerException if username is null
     */
    Librarian findLibrarianByUsername(String username);
    
    /**
     * Find all librarians
     * 
     * @return all librarians
     */
    List<Librarian> findAllLibrarians();
    
    
}
