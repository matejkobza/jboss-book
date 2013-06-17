package cz.muni.fi.jboss.book.persistence.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
 *
 */
public class AuthorDAOImplTest {

	private AuthorDAOImpl aDao;
	private Author author;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
	private EntityManager em;	
	
	@Before
	public void setUp(){
		aDao = new AuthorDAOImpl();
		em = emf.createEntityManager();
		author = createTestAuthor();
	}
	
	
	private void simulateBeginTransaction() {
		em = emf.createEntityManager();
		aDao.setEm(em);
		em.getTransaction().begin();
	}

	private void simulateEndTransaction() {
		em.getTransaction().commit();
		em.close();
	}
	
	private Book createTestBookWithAuthor(Author author) {
		Book book = new Book();
		book.setAuthor(author);
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
	
	private Author createTestAuthor(){
		Author author = new Author();
		author.setFirstName("William");
		author.setSurname("Shakespeare");
		author.setDescription("englishman");
		return author;
	}
	
	@Test
	public void testCreateAuthor(){
		try{
			simulateBeginTransaction();
			aDao.createAuthor(null);
			simulateEndTransaction();
			System.out.println("Should have thrown NPException, when creating null author");
			assertTrue(false);
		}catch (NullPointerException ex){
			assertTrue(true);
		}
		Author result = null;
		Book book1 = createTestBookWithAuthor(result);
		Book book2 = createTestBookWithAuthor(result);
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		author.setBooks(books);
		try{
			simulateBeginTransaction();
			result = aDao.createAuthor(author);
			simulateEndTransaction();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		assertNotNull(result.getId());
		assertEquals(author.getDescription(), result.getDescription());
		assertEquals(author.getFirstName(), result.getFirstName());
		assertEquals(author.getSurname(), result.getSurname());
		assertTrue(result.getBooks().contains(book1));
		assertTrue(result.getBooks().contains(book2));
		assertTrue(result.getBooks().equals(books));
	}
	
	@Test
	public void testUpdateAuthor(){
		try{
			simulateBeginTransaction();
			aDao.updateAuthor(null);
			simulateEndTransaction();
			System.out.println("Should have thrown NPException, when trying update null author");
			assertTrue(false);
		}catch (NullPointerException ex){
		}
		Author myAuthor = new Author();
		author.setId(null);
		try{
			simulateBeginTransaction();
			aDao.updateAuthor(myAuthor);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException, " +
					"when trying update author with null id");
			assertTrue(false);
		}catch (IllegalArgumentException ex){
		}
		
		Author result = null;
		Book book1 = createTestBookWithAuthor(result);
		Book book2 = createTestBookWithAuthor(result);
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		author.setBooks(books);
		try{
			simulateBeginTransaction();
			result = aDao.createAuthor(author);
			simulateEndTransaction();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		/* set different values to fields */
		result.setDescription("different desc");
		result.setFirstName("new first name");
		result.setSurname("new Surname");
		Book book3 = createTestBookWithAuthor(result);
		books.add(book3);
		result.setBooks(books);
		author = result;
		
		try{
			simulateBeginTransaction();
			result = aDao.updateAuthor(author);
			simulateEndTransaction();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		assertNotNull(result.getId());
		assertEquals(author.getDescription(), result.getDescription());
		assertEquals(author.getFirstName(), result.getFirstName());
		assertEquals(author.getSurname(), result.getSurname());
		assertTrue(result.getBooks().contains(book1));
		assertTrue(result.getBooks().contains(book2));
		assertTrue(result.getBooks().equals(books));
		
	}
	
	@Test
	public void testDeleteAuthor(){
		try{
			simulateBeginTransaction();
			aDao.deleteAuthor(null);
			simulateEndTransaction();
			System.out.println("Should have thrown NPException, when trying delete null author");
			assertTrue(false);
		}catch (NullPointerException ex){
		}
		Author myAuthor = new Author();
		author.setId(null);
		try{
			simulateBeginTransaction();
			aDao.deleteAuthor(myAuthor);
			simulateEndTransaction();
			System.out.println("Should have thrown IllegalArgumentException," +
					" when trying delete author with null id");
			assertTrue(false);
		}catch (IllegalArgumentException ex){
		}
		Author result = null;
		Book book1 = createTestBookWithAuthor(result);
		Book book2 = createTestBookWithAuthor(result);
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		author.setBooks(books);
		try{
			simulateBeginTransaction();
			result = aDao.createAuthor(author);
			simulateEndTransaction();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		author = result;
		
		try{
			simulateBeginTransaction();
			aDao.deleteAuthor(author);
			simulateEndTransaction();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		simulateBeginTransaction();
		List<Author> allAuthors = aDao.findAllAuthors();
		simulateEndTransaction();
		assertFalse(allAuthors.contains(author));
	}
	
	@Test
	public void testFindAuthor(){
		simulateBeginTransaction();
		aDao.createAuthor(author);
		simulateEndTransaction();
		simulateBeginTransaction();
		List<Author> authors = aDao.findAllAuthors();
		simulateEndTransaction();
		assertTrue(authors.contains(author));
	}

}
