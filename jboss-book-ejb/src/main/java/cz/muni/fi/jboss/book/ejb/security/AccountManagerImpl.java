package cz.muni.fi.jboss.book.ejb.security;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.ejb3.annotation.Clustered;

import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.dao.UserDAO;
import cz.muni.fi.jboss.book.persistence.entity.User;

@Dependent
@Named("accountManager")
@Stateless
@Remote
@Clustered
public class AccountManagerImpl implements AccountManager {
	
	@Inject
	private UserDAO userDao;
	
	/* (non-Javadoc)
	 * @see cz.muni.fi.jboss.book.ejb.security.RegistrationManager#register(cz.muni.fi.jboss.book.persistence.entity.User)
	 */
	@Override
	public User register(User user) {
		// TODO - set to manager if there's no manager
		user.setUserRole(UserRole.READER);
		return userDao.createUser(user);
	}

    @Override
    public User find(String username) {
        return userDao.findUserByUsername(username);
    }

}

