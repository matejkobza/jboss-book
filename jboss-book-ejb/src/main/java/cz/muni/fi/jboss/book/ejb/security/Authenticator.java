package cz.muni.fi.jboss.book.ejb.security;

import javax.ejb.Local;

@Local
public interface Authenticator {
	boolean authenticate();
}
