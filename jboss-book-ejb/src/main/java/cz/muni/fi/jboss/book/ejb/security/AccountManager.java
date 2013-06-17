package cz.muni.fi.jboss.book.ejb.security;

import cz.muni.fi.jboss.book.persistence.entity.User;

public interface AccountManager {

	public abstract void register(User user);

    public User find(String username);

}