package cz.muni.fi.jboss.book.ejb.security;

import java.util.List;

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
		// TODO - do this more effectively
		List<User> users = userDao.findAllUsers();
		boolean managerExists = false;
		for (User oneUser : users) {
			if (oneUser.getUserRole().equals(UserRole.MANAGER)) {
				managerExists = true;
				break;
			}
		}
		
		user.setUserRole(managerExists ? UserRole.READER : UserRole.MANAGER);
		return userDao.createUser(user);
	}

    @Override
    public User find(String username) {
        return userDao.findUserByUsername(username);
    }

}

