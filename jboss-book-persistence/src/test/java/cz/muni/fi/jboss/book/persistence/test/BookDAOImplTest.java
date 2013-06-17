package cz.muni.fi.jboss.book.persistence.test;

import java.util.List;

import cz.muni.fi.jboss.book.persistence.dao.AuthorDAOImpl;
import cz.muni.fi.jboss.book.persistence.dao.BookDAOImpl;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Eduard Tomek
 */
public class BookDAOImplTest {

	private BookDAOImpl bDao;
	private Book testBook1;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
	private EntityManager em;
	
	@Before
	public void setUp(){
		bDao = new BookDAOImpl();
		em = emf.createEntityManager();
		testBook1 = createTestBook1();
	}
	

	private Book createTestBook1() {
		Book book = new Book();
		book.setAuthor(createAuthor());
		book.setISBN(1000l);
		book.setTitle("Test bookname1");
		book.setPages(100);
		book.setPublisher("Test publisher1");
		return book;
	}
	
	private Author createAuthor(){
		Author author = new Author();
		author.setDescription("test description");
		author.setFirstName("Karel");
		author.setSurname("Polacek");
		
		AuthorDAOImpl authorDao = new AuthorDAOImpl();
		authorDao.setEm(emf.createEntityManager());
		authorDao.getEm().getTransaction().begin();
		authorDao.createAuthor(author);
		authorDao.getEm().getTransaction().commit();
		
		return author;
	}
	@Test
	public void testCreateNullBook() {
		try {
			simulateBeginTransaction();
			bDao.createBook(null);
			simulateEndTransaction();
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateBookWithNullFields() {
		Book book = new Book();
		try {
			simulateBeginTransaction();
			bDao.createBook(book);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateBookWithNullISBN() {
		testBook1.setISBN(null);
		try {
			simulateBeginTransaction();
			bDao.createBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateBookWithNegativeISBN() {
		testBook1.setISBN(Long.MIN_VALUE);
		try {
			simulateBeginTransaction();
			bDao.createBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateBookWithNullPages() {
		testBook1.setPages(null);
		try {
			simulateBeginTransaction();
			bDao.createBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateBookWithNegativePages() {
		testBook1.setPages(Integer.MIN_VALUE);
		try {
			simulateBeginTransaction();
			bDao.createBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateBook() {
		simulateBeginTransaction();
		Book result = null;
		try{
			result = bDao.createBook(testBook1);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
		simulateEndTransaction();
		assertNotNull(result.getId());
		assertEquals(testBook1.getISBN(), result.getISBN());
		assertEquals(testBook1.getPages(), result.getPages());
		assertEquals(testBook1.getPublisher(), result.getPublisher());
		assertEquals(testBook1.getTitle(), result.getTitle());
	}
	
	@Test
	public void testUpdateBookWithNull() {
		simulateBeginTransaction();
		bDao.createBook(testBook1);
		simulateEndTransaction();
		try {
			simulateBeginTransaction();
			bDao.updateBook(null);
			simulateEndTransaction();
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateBookWithNullISBNOrId() {
		simulateBeginTransaction();
		bDao.createBook(testBook1);
		simulateEndTransaction();
		testBook1.setISBN(null);
		try {
			simulateBeginTransaction();
			bDao.updateBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
		
		testBook1.setISBN(Long.MAX_VALUE);
		testBook1.setId(null);
		try {
			simulateBeginTransaction();
			bDao.updateBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateBookWithNegativeISBN() {
		simulateBeginTransaction();
		bDao.createBook(testBook1);
		simulateEndTransaction();
		testBook1.setISBN(Long.MIN_VALUE);
		try {
			simulateBeginTransaction();
			bDao.updateBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateBookWithNegativePages() {
		simulateBeginTransaction();
		bDao.createBook(testBook1);
		simulateEndTransaction();
		testBook1.setPages(Integer.MIN_VALUE);
		try {
			simulateBeginTransaction();
			bDao.updateBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateBookWithNullPages() {
		simulateBeginTransaction();
		bDao.createBook(testBook1);
		simulateEndTransaction();
		testBook1.setPages(null);
		try {
			simulateBeginTransaction();
			bDao.updateBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testDeleteBookWithNullOrNullId() {
		try {
			simulateBeginTransaction();
			bDao.deleteBook(null);
			simulateEndTransaction();
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		testBook1.setId(null);
		try {
			simulateBeginTransaction();
			bDao.updateBook(testBook1);
			simulateEndTransaction();
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
		
	}

	@Test
	public void testDeleteBook() {
		simulateBeginTransaction();
		testBook1 = bDao.createBook(testBook1);
		simulateEndTransaction();	
		simulateBeginTransaction();
		
		bDao.deleteBook(testBook1);
		simulateEndTransaction();		
		simulateBeginTransaction();
		List<Book> books = bDao.findAllBooks(); 
		simulateEndTransaction();
		assertFalse(books.contains(testBook1));
}
	@Test
	public void testFindBook(){
		simulateBeginTransaction();
		bDao.createBook(testBook1);
		simulateEndTransaction();
		simulateBeginTransaction();
		List<Book> books = bDao.findBookByAuthor(testBook1.getAuthor());
		simulateEndTransaction();
		assertTrue(books.contains(testBook1));
		
		simulateBeginTransaction();
		books = bDao.findBookByISBN(testBook1.getISBN());
		simulateEndTransaction();
		assertTrue(books.contains(testBook1));
		
		simulateBeginTransaction();
		books = bDao.findBookByTitle(testBook1.getTitle());
		simulateEndTransaction();
		assertTrue(books.contains(testBook1));
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
	
	
}
