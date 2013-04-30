package cz.muni.fi.jboss.book.persistence.dao.impl;

import cz.muni.fi.jboss.book.persistence.dao.BookCopyDAO;
import cz.muni.fi.library.entity.Book;
import cz.muni.fi.library.entity.BookCopy;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class BookCopyDAOImpl implements BookCopyDAO {

  @PersistenceContext
  private EntityManager em;

  public EntityManager getEm() {
    return em;
  }

  public void setEm(EntityManager em) {
    this.em = em;
  }

  @Override
  public BookCopy createBookCopy(BookCopy bookCopy) {
    if (bookCopy == null) {
      throw new NullPointerException("bookCopy is null");
    }
    if (bookCopy.getBook() == null || bookCopy.getPurchaseDate() == null) {
      throw new IllegalArgumentException("book or purchase date is null");
    }
    em.persist(bookCopy);
    em.flush();
    return bookCopy;
  }

  @Override
  public BookCopy updateBookCopy(BookCopy bookCopy) {
    if (bookCopy == null) {
      throw new NullPointerException("bookCopy is null");
    }
    if (bookCopy.getBook() == null || bookCopy.getPurchaseDate() == null) {
      throw new IllegalArgumentException("book or purchase date is null");
    }
    BookCopy dbBC = em.find(BookCopy.class, bookCopy);
    dbBC.setBook(bookCopy.getBook());
    dbBC.setPurchaseDate(bookCopy.getPurchaseDate());
    em.persist(dbBC);
    em.flush();
    return dbBC;
  }

  @Override
  public void deleteBookCopy(BookCopy bookCopy) {
    if (bookCopy == null) {
      throw new NullPointerException("bookCopy is null");
    }
    em.remove(bookCopy);
  }

  @Override
  public BookCopy findBookCopyById(Long id) {
    if (id == null) {
      throw new NullPointerException("id is null");
    }
    return em.find(BookCopy.class, id);
  }

  @Override
  public List<BookCopy> findBookCopyByBook(Book book) {
    if (book == null) {
      throw new NullPointerException("book is null");
    }
    return em.createQuery("SELECT b FROM BookCopy b WHERE b.book == :book").getResultList();
  }

  @Override
  public List<BookCopy> findAllBookCopies() {
    return em.createQuery("SELECT b FROM BookCopy b").getResultList();
  }
}
