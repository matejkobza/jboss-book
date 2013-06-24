package cz.muni.fi.jboss.book.ejb.test;

import java.util.List;

import javax.ejb.BeforeCompletion;
import javax.inject.Inject;
import javax.inject.Named;
import static org.junit.Assert.*

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence10.PersistenceDescriptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.muni.fi.jboss.book.ejb.manager.AuthorManager;
import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.ejb.manager.BookManagerImpl;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import javax.inject.Inject;

/**
 * 
 * @author Eduard Tomek
 *
 */
@RunWith(Arquillian.class)
public class BookManagerTest {

	@Deployment
    public static JavaArchive createDeployment() {
		
		 PersistenceDescriptor persistence = Descriptors.create(PersistenceDescriptor.class)
	                .createPersistenceUnit()
	                    .name("PU")
	                    .jtaDataSource("java:/DerbyDS")
	                .up();
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackage("cz.muni.fi.jboss.book.ejb.manager")
            .addPackage("cz.muni.fi.jboss.book.persistence")
            .addPackage("cz.muni.fi.jboss.book.persistence.entity")
            .addPackage("cz.muni.fi.jboss.book.persistence.dao")
             .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsResource(new StringAsset(persistence.exportAsString()), "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
    }
	
	@Inject
	private AuthorManager authorManager;
	
	@Inject
	private BookManager bookManager;
	
	private Author testAuthor;
	
	@Before
	public void setUp(){
		createTestAuthor();
	}
	
	private void createTestAuthor(){
		Author author = new Author();
		author.setDescription("test");
		author.setFirstName("Jiri");
		author.setSurname("Novak");
		testAuthor = authorManager.create(author);
	}
	
	private Book createTestBook(){
		Book book = new Book();
		book.setAuthor(testAuthor);
		book.setISBN(1000l);
		book.setTitle("Test bookname1");
		book.setPages(100);
		book.setPublisher("Test publisher1");
		return book;
	}
	
	@Test
	public void testCreateBook(){
		Book book = createTestBook()
		try{
			bookManager.addBook(book);
		}catch (Exception ex){
			fail();
		}
		assertNotNull(book.getId());
		Book book2 = createTestBook();
		assertEquals(book2.getISBN(), book.getISBN());
		assertEquals(book2.getPages(), book.getPages());
		assertEquals(book2.getPublisher(), book.getPublisher());
		assertEquals(book2.getTitle(), book.getTitle());
	}
	
	@Test
	public void testUpdateBook(){
		Book book = createTestBook()
		try{
			bookManager.addBook(book);
		}catch (Exception ex){
			fail();
		}
		Long longg = 121;
		String test = "Test";
		String testPub = "TestPub";
		
		book.setISBN(longg);
		book.setTitle(test);
		book.setPages(longg);
		book.setPublisher(testPub);
		try{
			bookManager.updateBook(book);
		}catch (Exception ex){
			fail();
		}
		
		assertEquals(longg, book.getISBN());
		assertEquals(longg, book.getPages());
		assertEquals(testPub, book.getPublisher());
		assertEquals(test, book.getTitle());
	}
	
	
	
	
	
	
}