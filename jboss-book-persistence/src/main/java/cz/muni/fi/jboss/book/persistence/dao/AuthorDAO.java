package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;

/**
 * Interface BookCopyDAO
 *
 * @author Eduard Tomek
 *
 */

public interface AuthorDAO {
	/**
	   * Create author
	   *
	   * @param author author which will be created
	   * @return created author
	   * @throws NullPointerException if author is null
	   */
	  Author createAuthor(Author author);

	  /**
	   * Update author
	   *
	   * @param author author to update
	   * @return updated author
	   * @throws NullPointerException if author is null
	   */
	  Author updateAuthor(Author author);

	  /**
	   * Delete author
	   *
	   * @param author author which will be deleted
	   */
	  void deleteAuthor(Author author);

	  /**
	   * Find author by Id
	   *
	   * @param id id of author
	   * @return author with given id, null if there is no such author
	   */
	  Author findAuthorById(Long id);

	  /**
	   * Find all book authors
	   *
	   * @return all book authors
	   */
	  List<Author> findAllAuthors();
}
