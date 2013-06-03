package cz.muni.fi.jboss.book.ejb.security;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.entity.User;

@Singleton
@Startup
public class UserInitializer {
	
	@EJB
	private AccountManager accountManager;

	@PostConstruct
	public void create() {
		// create one reader
		User reader = new User();
		reader.setName("Mr. Reader");
		reader.setUsername("reader");
		reader.setPassword("reader");
		reader.setUserRole(UserRole.READER);
		
		accountManager.register(reader);
	}

}
