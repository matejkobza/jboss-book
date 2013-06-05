package cz.muni.fi.jboss.book.persistence.dao;

import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class BookDAOImpl implements BookDAO {

  @PersistenceContext(unitName = "PU")
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
      throw new IllegalArgumentException(
              "pages or ISBN is null or negative");
    }
    em.persist(book);
    em.flush();
    return book;
  }

  @Override
  public Book updateBook(Book book) {
    if (book == null) {
      throw new NullPointerException("book is null");
    }
    if (book.getISBN() == null 
    		|| book.getPages() == null
            || book.getISBN() < 0 
            || book.getPages() < 0
            || book.getId() == null) {
      throw new IllegalArgumentException(
              "id, pages or ISBN is null or negative");
    }
    Book dbBook = em.find(Book.class, book.getId());
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
    Book dbBook = findBookById(book.getId());
    em.remove(dbBook);
    em.flush();
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
    List<Book> books = em.createQuery(
            "SELECT b FROM Book b WHERE b.ISBN = :ISBN")
            .setParameter("ISBN", ISBN).getResultList();
    return books;
  }

  @Override
  public List<Book> findBookByTitle(String title) {
    if (title == null) {
      throw new NullPointerException("title is null");
    }
    List<Book> books = em.createQuery(
            "SELECT b FROM Book b WHERE b.title = :title")
            .setParameter("title", title).getResultList();
    return books;
  }
  
  

  @Override
  public List<Book> findBookByTitlePart(String titlePart) {
	    if (titlePart == null) {
	        throw new NullPointerException("titlePart is null");
	      }
	    return em.createQuery(
	            "SELECT b FROM Book b WHERE UPPER(b.title) LIKE UPPER(:title)")
	            .setParameter("title", "%" + titlePart + "%").getResultList();
  }

@Override
  public List<Book> findBookByAuthor(Author author) {
    if (author == null) {
      throw new NullPointerException("author is null");
    }
    List<Book> books = em.createQuery(
            "SELECT b FROM Book b WHERE b.author = :author")
            .setParameter("author", author).getResultList();
    return books;
  }

  @Override
  public List<Book> findAllBooks() {
    return em.createQuery("SELECT b FROM Book b").getResultList();
  }
}
