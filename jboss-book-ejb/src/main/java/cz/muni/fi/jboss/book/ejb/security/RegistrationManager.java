package cz.muni.fi.jboss.book.ejb.security;

import cz.muni.fi.jboss.book.persistence.entity.User;

public interface RegistrationManager {

	public abstract void register(User user);

}