package cz.muni.fi.jboss.book.persistence.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javassist.bytecode.annotation.LongMemberValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.dao.AuthorDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.BookCopyDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.BookCopyReservationDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.BookDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.UserDAOImpl;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;

/**
 * 
 * @author Eduard Tomek
 *
 */
public class BookCopyReservationDAOImplTest{
	private BookCopyReservationDAOImpl bDao;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
	private EntityManager em;
	private BookCopyReservation testBookCopyReservation1;
	private static User user;
	
	@BeforeClass
	public static void beforeClass(){
		user = createTestUser1();
	}
	
	@Before
	public void setUp(){
		bDao = new BookCopyReservationDAOImpl();
		em = emf.createEntityManager();
		testBookCopyReservation1 = createTestBookCopyReservation();
	}
	
	private BookCopyReservation createTestBookCopyReservation(){
		BookCopyReservation newReservation = new BookCopyReservation();
		newReservation.setBookCopy(createTestBookCopy());
		newReservation.setReservationState(ReservationState.NEW);
		newReservation.setUser(user);
		newReservation.setCreated(new Date(2012, 1, 1));
		return newReservation;
	}
	
	private BookCopy createTestBookCopy(){
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBook(createTestBook());
		bookCopy.setPurchaseDate(new Date());
		BookCopyDAOImpl bDao = new BookCopyDAOImpl();
		bDao.setEm(emf.createEntityManager());
		bDao.getEm().getTransaction().begin();
		bookCopy = bDao.createBookCopy(bookCopy);
		bDao.getEm().getTransaction().commit();
		return bookCopy;
	}
	
	
	private Book createTestBook() {
		Book book = new Book();
		book.setAuthor(createAuthor());
		book.setISBN(1000l);
		book.setTitle("Lord of the rings");
		book.setPages(400);
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
		author.setFirstName("Johnny");
		author.setSurname("Tolkien");
		AuthorDAOImpl authorDao = new AuthorDAOImpl();
		authorDao.setEm(emf.createEntityManager());
		authorDao.getEm().getTransaction().begin();
		authorDao.createAuthor(author);
		authorDao.getEm().getTransaction().commit();
		return author;
	}
	//call it only once, because of uniqueness of username
	private static User createTestUser1(){
		User user = new User();
		user.setName("John Lennon");
		user.setPassword("###Hash####");
		user.setUsername("Lenny");
		user.setUserRole(UserRole.MANAGER);
		UserDAOImpl uDao = new UserDAOImpl();
		uDao.setEm(emf.createEntityManager());
		uDao.getEm().getTransaction().begin();
		user = uDao.createUser(user);
		uDao.getEm().getTransaction().commit();
		uDao.getEm().close();
		return user;
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
	public void testCreateBookCopyReservation(){
		try{
			simulateBeginTransaction();
			bDao.createBookCopyReservation(null);
			simulateEndTransaction();
			System.out.println("Should have thrown NPException");
			assertTrue(false);
		}catch(NullPointerException ex){}
		BookCopyReservation myBCReservation = new BookCopyReservation();
		myBCReservation.setReservationState(null);
		myBCReservation.setBookCopy(createTestBookCopy());
		try{
			simulateBeginTransaction();
			bDao.createBookCopyReservation(myBCReservation);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, state is null");
			assertTrue(false);
		}catch(IllegalArgumentException ex){}
		
		myBCReservation.setReservationState(ReservationState.LENT);
		myBCReservation.setBookCopy(null);
		try{
			simulateBeginTransaction();
			bDao.createBookCopyReservation(myBCReservation);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, book copy is null");
			assertTrue(false);
		}catch(IllegalArgumentException ex){}
		
		BookCopyReservation result = null;
		try{
			simulateBeginTransaction();
			result = bDao.createBookCopyReservation(testBookCopyReservation1);
			simulateEndTransaction();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		assertNotNull(result.getId());
		assertEquals(testBookCopyReservation1.getBookCopy(), result.getBookCopy());
		assertEquals(testBookCopyReservation1.getReservationState(), result.getReservationState());
	}
	
	@Test
	public void testUpdateBookCopyReservation(){
		try{
			simulateBeginTransaction();
			bDao.updateBookCopyReservation(null);
			simulateEndTransaction();
			System.out.println("Should have thrown NPException");
			assertTrue(false);
		}catch(NullPointerException ex){}
		
		BookCopyReservation myBCReservation = new BookCopyReservation();
		myBCReservation.setReservationState(null);
		myBCReservation.setId(Long.MAX_VALUE);
		myBCReservation.setBookCopy(createTestBookCopy());
		try{ //reservation state is null
			simulateBeginTransaction();
			bDao.updateBookCopyReservation(myBCReservation);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, state is null");
			assertTrue(false);
		}catch(IllegalArgumentException ex){}
		
		myBCReservation.setReservationState(ReservationState.LENT);
		myBCReservation.setBookCopy(null);
		try{ //book copy is null
			simulateBeginTransaction();
			bDao.updateBookCopyReservation(myBCReservation);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, book copy is null");
			assertTrue(false);
		}catch(IllegalArgumentException ex){}
		
		myBCReservation.setReservationState(ReservationState.READY);
		myBCReservation.setId(null);
		myBCReservation.setBookCopy(createTestBookCopy());
		try{ //id is null
			simulateBeginTransaction();
			bDao.updateBookCopyReservation(myBCReservation);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, book copy is null");
			assertTrue(false);
		}catch(IllegalArgumentException ex){}
		
		simulateBeginTransaction();
		bDao.createBookCopyReservation(testBookCopyReservation1);
		simulateEndTransaction();
		testBookCopyReservation1.setBookCopy(createTestBookCopy());
		testBookCopyReservation1.setReservationState(ReservationState.RETURNED);
		BookCopyReservation resultOfUpdate = null;
		try{
			simulateBeginTransaction();
			resultOfUpdate = bDao.updateBookCopyReservation(testBookCopyReservation1);
			simulateEndTransaction();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		assertEquals(testBookCopyReservation1.getBookCopy(), resultOfUpdate.getBookCopy());
		assertEquals(testBookCopyReservation1.getReservationState(), resultOfUpdate.getReservationState());
	}
	
	@Test
	public void testDeleteBookCopyReservation(){
		try{
			simulateBeginTransaction();
			bDao.deleteBookCopyReservation(null);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, " +
					"book copy is null");
			assertFalse(true);
		}catch (NullPointerException ex){}

		BookCopyReservation myBCReservation = new BookCopyReservation();
		myBCReservation.setId(null);
		try{
			simulateBeginTransaction();
			bDao.deleteBookCopyReservation(myBCReservation);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, " +
					"book copy's id is null");
			assertFalse(true);
		}catch (IllegalArgumentException ex){}
		
		simulateBeginTransaction();
		bDao.createBookCopyReservation(testBookCopyReservation1);
		simulateEndTransaction();
		simulateBeginTransaction();
		bDao.deleteBookCopyReservation(testBookCopyReservation1);
		simulateEndTransaction();
		simulateBeginTransaction();
		List<BookCopyReservation> reservations = bDao.findAllBookCopyReservations();
		simulateEndTransaction();
		assertFalse(reservations.contains(testBookCopyReservation1));
	}

	@Test
	public void testFindBookCopyReservations(){
		simulateBeginTransaction();
		bDao.createBookCopyReservation(testBookCopyReservation1);
		simulateEndTransaction();
		
		simulateBeginTransaction();
		List<BookCopyReservation> reservations = 
				bDao.findBookCopyReservationsByReservationState(
						testBookCopyReservation1.getReservationState());
		simulateEndTransaction();
		assertTrue(reservations.contains(testBookCopyReservation1));
		
		simulateBeginTransaction();
		reservations = bDao.findBookCopyReservationsByBookCopy(
				testBookCopyReservation1.getBookCopy());
		simulateEndTransaction();
		assertTrue(reservations.contains(testBookCopyReservation1));
	}
	
	
	
	
	
}
