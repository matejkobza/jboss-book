package cz.muni.fi.jboss.book.persistence.dao;

import cz.muni.fi.jboss.book.persistence.dao.BookDAO;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class BookDAOImpl implements BookDAO {

  @PersistenceContext(unitName="PU")
  private EntityManager em;

  public EntityManager getEm() {
    return em;
  }

  public void setEm(EntityManager em) {
    this.em = em;
  }

  @Override
  public Book createBook(Book book) {
    if (book == null) {
      throw new NullPointerException("book is null");
    }
    if (book.getISBN() == null || book.getPages() == null
            || book.getISBN() < 0 || book.getPages() < 0) {
      throw new IllegalArgumentException("pages or ISBN is null or negative");
    }
    em.getTransaction().begin();
    em.persist(book);
    em.flush();
    //em.getTransaction().commit();
    return book;
  }

  @Override
  public Book updateBook(Book book) {
    if (book == null) {
      throw new NullPointerException("book is null");
    }
    if (book.getISBN() == null || book.getPages() == null
            || book.getISBN() < 0 || book.getPages() < 0) {
      throw new IllegalArgumentException("pages or ISBN is null or negative");
    }
    Book dbBook = em.find(Book.class, book.getId());
    dbBook.setAuthor(book.getAuthor());
    dbBook.setPublisher(book.getPublisher());
    dbBook.setTitle(book.getTitle());
    dbBook.setISBN(book.getISBN());
    dbBook.setPages(book.getPages());
    em.persist(dbBook);
    em.flush();
    return dbBook;
  }

  @Override
  public void deleteBook(Book book) {
    if (book == null) {
      throw new NullPointerException("book is null");
    }
    em.getTransaction().begin();
    em.remove(book);
    em.flush();
    em.getTransaction().commit();    
  }

  @Override
  public Book findBookById(Long id) {
    if (id == null) {
      throw new NullPointerException("id is null");
    }
    return em.find(Book.class, id);
  }

  @Override
  public List<Book> findBookByISBN(Long ISBN) {
    if (ISBN == null) {
      throw new NullPointerException("ISBN is null");
    }
    List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.ISBN == :ISBN").getResultList();
    return books;
  }

  @Override
  public List<Book> findBookByTitle(String title) {
    if (title == null) {
      throw new NullPointerException("title is null");
    }
    List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.title == :title").getResultList();
    return books;
  }

  @Override
  public List<Book> findBookByAuthor(String author) {
    if (author == null) {
      throw new NullPointerException("author is null");
    }
    List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.author == :author").getResultList();
    return books;
  }

  @Override
  public List<Book> findAllBooks() {
    return em.createQuery("SELECT b FROM Book b").getResultList();
  }
}
