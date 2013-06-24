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
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.muni.fi.jboss.book.ejb.manager.BookManagerImpl;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import javax.inject.Inject;

@RunWith(Arquillian.class)
public class AuthorManagerTest {

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
	
	@Before
	private setUp(){
		
	}
	
	private Author createTestAuthorA(){
		Author author = new Author();
		author.setDescription("test descr");
		author.setFirstName("Radek");
		author.setSurname("Nejedly");
		return author;
	}
	
	@Test
	public void testCreateAuthor(){
		Author author = createTestAuthorA();
		try{
			autorManager.create(author);
		}catch(Exception ex){
			fail();
		}
		assertNotNull(author.getId());
		Author author2 = createTestAuthorA();
		assertEquals(author2.getDescription(), author.getDescription());
		assertEquals(author2.getFirstName(), author.getFirstName());
		assertEquals(author2.getSurname(), author.getSurname());	
	}
	
	
	
	
	
}
