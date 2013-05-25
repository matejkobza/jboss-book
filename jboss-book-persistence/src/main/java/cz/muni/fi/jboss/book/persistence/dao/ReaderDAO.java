package cz.muni.fi.jboss.book.persistence.dao;

import cz.muni.fi.jboss.book.persistence.entity.Reader;
import java.util.List;

/**
 *
 * @author Eduard Tomek
 */
public interface ReaderDAO {

  /**
   * Create reader
   *
   * @param reader
   * @return created reader
   * @throws NullPointerException if reader is null
   * @throws IllegalArgumentException if reader with given name already exists
   */
  Reader createReader(Reader reader);

  /**
   * Update reader
   *
   * @param reader
   * @return updated reader
   * @throws NullPointerException if reader is null
   * @throws IllegalArgumentException if reader with given name already exists
   */
  Reader updateReader(Reader reader);

  /**
   * Delete reader
   *
   * @param reader
   * @throws NullPointerException if reader is null
   */
  void deleteReader(Reader reader);

  /**
   * Find reader by id
   *
   * @param id
   * @return reader with given id
   * @throws NullPointerException if id is null
   */
  Reader findReaderById(String id);

  /**
   * Find reader by username
   *
   * @param username
   * @return reader with given username
   * @throws NullPointerException if username is null
   */
  Reader findReaderByUsername(String username);

  /**
   * Find all readers
   *
   * @return all readers
   */
  List<Reader> findAllReaders();
}
