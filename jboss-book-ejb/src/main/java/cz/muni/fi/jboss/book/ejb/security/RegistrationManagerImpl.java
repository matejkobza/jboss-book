package cz.muni.fi.jboss.book.ejb.security;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;

import cz.muni.fi.jboss.book.persistence.dao.UserDAO;
import cz.muni.fi.jboss.book.persistence.entity.User;

@Dependent
@Named("registrationManager")
@Stateless
@Remote
public class RegistrationManagerImpl implements RegistrationManager {
	
	@Inject
	private UserDAO userDao;
	
	@Inject
	Messages messages;
	
	/* (non-Javadoc)
	 * @see cz.muni.fi.jboss.book.ejb.security.RegistrationManager#register(cz.muni.fi.jboss.book.persistence.entity.User)
	 */
	@Override
	public void register(User user) {
		userDao.createUser(user);
	}
	
}

