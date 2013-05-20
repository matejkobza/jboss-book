package cz.muni.fi.jboss.book.ejb.test;

import java.util.List;

import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.api.annotation.Inject;
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
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;

@RunWith(Arquillian.class)
public class BookReservationTest {

	@Deployment
    public static JavaArchive createDeployment() {
		
		 PersistenceDescriptor persistence = Descriptors.create(PersistenceDescriptor.class)
	                .createPersistenceUnit()
	                    .name("PU")
	                    .jtaDataSource("java:/DerbyDS")
	                .up();
		 System.out.println(persistence.exportAsString());

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackage("cz.muni.fi.jboss.book.ejb.manager")
            .addPackage("cz.muni.fi.jboss.book.persistence")
            .addPackage("cz.muni.fi.jboss.book.persistence.entity")
            .addPackage("cz.muni.fi.jboss.book.persistence.dao")
             .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsResource(new StringAsset(persistence.exportAsString()), "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		       
        System.out.println(jar.toString(true));
        
        return jar;
    }
	
	@org.jboss.arquillian.core.api.annotation.Inject @Named("reservationManager")
	private BookManagerImpl fBookManager;
	
	
	
	
	@Test 
	public void testInject() {
		fBookManager = new BookManagerImpl();
		Assert.assertNotNull(fBookManager);
		List<Book> books = fBookManager.findAllBooks();
		Assert.assertNotNull(books);
	}
	
	@org.jboss.arquillian.core.api.annotation.Inject BookCopy fBookCopy;
	@Inject Book fBook;
	@Test
	public void test() {
		Assert.assertNotNull(fBookCopy);
	}
}