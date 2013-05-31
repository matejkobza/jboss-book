package cz.muni.fi.jboss.book.ejb.security;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.jboss.book.persistence.dao.UserDAO;
import cz.muni.fi.jboss.book.persistence.entity.User;

@Dependent
@Named("accountManager")
@Stateless
@Remote
public class AccountManagerImpl implements AccountManager {
	
	@Inject
	private UserDAO userDao;
	
	/* (non-Javadoc)
	 * @see cz.muni.fi.jboss.book.ejb.security.RegistrationManager#register(cz.muni.fi.jboss.book.persistence.entity.User)
	 */
	@Override
	public void register(User user) {
		userDao.createUser(user);
	}

	@Override
	public boolean login(User user) {
		return userDao.authenticate(user);
	}
	
}

