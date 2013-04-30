package cz.muni.fi.jboss.book.persistence.dao.impl;

import cz.muni.fi.jboss.book.persistence.dao.ReaderDAO;
import cz.muni.fi.jboss.book.persistence.entity.Reader;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class ReaderDAOImpl implements ReaderDAO {

  @PersistenceContext
  EntityManager em;

  @Override
  public Reader createReader(Reader reader) {
    if (reader == null) {
      throw new NullPointerException("reader is null");
    }
    if (findReaderByUsername(reader.getUsername()) != null) {
      throw new IllegalArgumentException("reader with given username alrady exists");
    }
    em.persist(reader);
    em.flush();
    return reader;
  }

  @Override
  public Reader updateReader(Reader reader) {
    if (reader == null) {
      throw new NullPointerException("reader is null");
    }
    if (findReaderByUsername(reader.getUsername()) != null) {
      throw new IllegalArgumentException("reader with given username alrady exists");
    }
    Reader dbReader = findReaderById(reader.getId());
    dbReader.setAboutMe(reader.getAboutMe());
    dbReader.setName(reader.getName());
    dbReader.setPassword(reader.getPassword());
    dbReader.setUsername(reader.getUsername());
    em.persist(dbReader);
    em.flush();
    return dbReader;
  }

  @Override
  public void deleteReader(Reader reader) {
    if (reader == null) {
      throw new NullPointerException("reader is null");
    }
    em.remove(reader);
  }

  @Override
  public Reader findReaderById(Long id) {
    if (id == null) {
      throw new NullPointerException("id is null");
    }
    return em.find(Reader.class, id);
  }

  @Override
  public Reader findReaderByUsername(String username) {
    if (username == null) {
      throw new NullPointerException("username is null");
    }
    Reader result;
    try {
      result = (Reader) em.createQuery("SELECT r FROM User r "
              + "WHERE r.username == :username").getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
    return result;
  }

  @Override
  public List<Reader> findAllReaders() {
    return em.createQuery("SELECT r FROM User r").getResultList();
  }
}
