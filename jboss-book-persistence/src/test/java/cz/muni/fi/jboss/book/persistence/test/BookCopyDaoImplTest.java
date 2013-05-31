package cz.muni.fi.jboss.book.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.jboss.book.persistence.dao.AuthorDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.BookCopyDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.BookDAOImpl;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;

/**
 * 
 * @author Eduard Tomek
 */
@Local
public class BookCopyDaoImplTest {
	private BookCopyDAOImpl bDao;
	private BookCopy testBookCopy1;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
	private EntityManager em;
	
	@Before
	public void setUp(){
		bDao = new BookCopyDAOImpl();
		em = emf.createEntityManager();
		testBookCopy1 = createTestBookCopy1();
	}
	
	private BookCopy createTestBookCopy1(){
		BookCopy bookCopy = new BookCopy();
		bookCopy.setPurchaseDate(new Date());
		bookCopy.setBook(createTestBook1());
		return bookCopy;
	}
	
	private Book createTestBook1() {
		Book book = new Book();
		book.setAuthor(createAuthor());
		book.setISBN(1000l);
		book.setTitle("Valka s mloky");
		book.setPages(100);
		book.setPublisher("Test publisher1");
		BookDAOImpl bookDao = new BookDAOImpl();
		bookDao.setEm(emf.createEntityManager());
		bookDao.getEm().getTransaction().begin();
		bookDao.createBook(book);
		bookDao.getEm().getTransaction().commit();
		return book;
	}
	
	private Author createAuthor(){
		Author author = new Author();
		author.setDescription("test description");
		author.setFirstName("Karel");
		author.setSurname("Capek");
		AuthorDAOImpl authorDao = new AuthorDAOImpl();
		authorDao.setEm(emf.createEntityManager());
		authorDao.getEm().getTransaction().begin();
		authorDao.createAuthor(author);
		authorDao.getEm().getTransaction().commit();
		return author;
	}
	
	private void simulateBeginTransaction() {
		em = emf.createEntityManager();
		bDao.setEm(em);
		em.getTransaction().begin();
	}

	private void simulateEndTransaction() {
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testCreateBookCopyNullOrIllegal(){
		try {
			simulateBeginTransaction();
			bDao.createBookCopy(null);
			simulateEndTransaction();
			System.out.println("Not thrown NullPointerException when creating null BookCopy");
			assertTrue(false);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		BookCopy bCopy = new BookCopy();
		try {
			simulateBeginTransaction();
			bDao.createBookCopy(bCopy); //has null purchase date
			simulateEndTransaction();
			System.out.println("Not thrown IllegalArgumentException when creating BookCopy "+
			 "with null purchaseDate");
			assertTrue(false);
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCreateBookCopy(){
		simulateBeginTransaction();
		BookCopy result = null;
		try{
			result = bDao.createBookCopy(testBookCopy1);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
		simulateEndTransaction();
		assertNotNull(result.getId());
		assertEquals(testBookCopy1.getPurchaseDate(), result.getPurchaseDate());
		assertEquals(testBookCopy1.getBook(), result.getBook());
	}
	
	@Test
	public void testUpdateBookCopyWithNullIdOrPurchaseDateOrBookCopy(){
		
		try {
			simulateBeginTransaction();
			bDao.updateBookCopy(null);
			simulateEndTransaction();
			System.out.println("Not thrown NullPointerException when updating null BookCopy");
			assertTrue(false);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		
		simulateBeginTransaction();
		bDao.createBookCopy(testBookCopy1);
		simulateEndTransaction();
		testBookCopy1.setPurchaseDate(null);
		try {
			simulateBeginTransaction();
			bDao.updateBookCopy(testBookCopy1);
			simulateEndTransaction();
			assertTrue(false);
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
		testBookCopy1.setPurchaseDate(new Date());
		testBookCopy1.setId(null);
		try {
			simulateBeginTransaction();
			bDao.updateBookCopy(testBookCopy1);
			simulateEndTransaction();
			assertTrue(false);
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUpdateBookCopy(){
		simulateBeginTransaction();
		bDao.createBookCopy(testBookCopy1);
		simulateEndTransaction();
		Date date = new Date();
		testBookCopy1.setPurchaseDate(date);
		BookCopy result = null;
		try {
			simulateBeginTransaction();
			result = bDao.updateBookCopy(testBookCopy1);
			simulateEndTransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		assertNotNull(result.getId());
		assertEquals(date, result.getPurchaseDate());
		assertEquals(testBookCopy1.getBook(), result.getBook());
	}
	
	@Test
	public void testDeleteBookCopy(){
		
		try{
			bDao.deleteBookCopy(null);
			System.out.println("Not thrown NullPointerException when deleting null BookCopy");
			assertTrue(false);
		}catch (NullPointerException ex){
			assertTrue(true);
		}
		BookCopy myBookCopy = new BookCopy();
		myBookCopy.setId(null);
		try{
			bDao.deleteBookCopy(myBookCopy);
			System.out.println("Not thrown IllegalArgumentException when deleting BookCopywith null id");
			assertTrue(false);
		}catch (IllegalArgumentException ex){
			assertTrue(true);
		}
		
		simulateBeginTransaction();
		testBookCopy1 = bDao.createBookCopy(testBookCopy1);
		simulateEndTransaction();
		
		simulateBeginTransaction();
		bDao.deleteBookCopy(testBookCopy1);
		simulateEndTransaction();
		simulateBeginTransaction();
		List<BookCopy> copies = bDao.findAllBookCopies();
		simulateEndTransaction();
		assertFalse(copies.contains(testBookCopy1));
	}
	
	@Test
	public void testFindBook(){
		simulateBeginTransaction();
		bDao.createBookCopy(testBookCopy1);
		List<BookCopy> copies = bDao.findBookCopyByBook(testBookCopy1.getBook());
		simulateEndTransaction();
		assertTrue(copies.contains(testBookCopy1));
	}
}
	

