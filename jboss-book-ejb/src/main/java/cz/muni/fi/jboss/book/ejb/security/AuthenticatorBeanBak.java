package cz.muni.fi.jboss.book.ejb.security;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import cz.muni.fi.jboss.book.persistence.dao.UserDAO;
import cz.muni.fi.jboss.book.persistence.entity.User;

public class AuthenticatorBeanBak extends BaseAuthenticator {
	@Inject
	Credentials credentials;

	@Inject
	UserDAO userDao;

	@EJB(name = "AccountManager")
	private AccountManager accountManager;

	@Override
	public void authenticate() {
		User user = new User();
		user.setUsername(credentials.getUsername());
		user.setPassword(((PasswordCredential) credentials.getCredential()).getValue());

		if (accountManager.login(user)) {
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(user);
		} else {
			setStatus(AuthenticationStatus.FAILURE);
		}
	}
}
