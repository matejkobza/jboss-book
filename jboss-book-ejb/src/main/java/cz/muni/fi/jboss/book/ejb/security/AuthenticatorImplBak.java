package cz.muni.fi.jboss.book.ejb.security;

import javax.inject.Inject;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;

import cz.muni.fi.jboss.book.persistence.dao.UserDAOImpl;

public class AuthenticatorImplBak extends BaseAuthenticator implements
		Authenticator {

	@Inject
	UserDAOImpl fUserDAOImpl;
	
	@Inject
	Identity fIdentity;
	
	@Inject 
	Credentials fCredentials;
	
	
	
	@Override
	public void authenticate() {
		
		
		System.out.println(fCredentials.getUsername());
		cz.muni.fi.jboss.book.persistence.entity.User user = fUserDAOImpl.findUserByUsername(fCredentials.getUsername());
		if (user == null) {

			setStatus(AuthenticationStatus.FAILURE);
			return;
		}
		
		if (user.getPassword().equals( ((PasswordCredential) fCredentials.getCredential()).getValue())) {
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(user);
		}
		
		setStatus(AuthenticationStatus.FAILURE);
	}

}
