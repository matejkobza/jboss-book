package cz.muni.fi.jboss.book.ejb.util;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.User;

@Singleton
@Startup
public class RecordInitializer {
	
	@EJB
	private AccountManager accountManager;
	
	@EJB
	private BookManager bookManager;
	
	private void createUsers() {
		// create one reader
		User reader = new User();
		reader.setName("Mr. Reader");
		reader.setUsername("reader");
		reader.setPassword("reader");
		reader.setUserRole(UserRole.READER);
		
		accountManager.register(reader);
	}
	
	private void createBooks() {
		Book book = new Book();
		book.setAuthor(null);
		book.setISBN(131103628L);
		book.setPages(274);
		book.setPublisher("Prentice Hall");
		book.setTitle("C Programming Language (2nd Edition)");
		
		bookManager.addBook(book);
	}

	@PostConstruct
	public void create() {
		createUsers();
		createBooks();
	}

}
