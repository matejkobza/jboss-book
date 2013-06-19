package cz.muni.fi.jboss.book.ejb.security;

import cz.muni.fi.jboss.book.persistence.entity.User;

public interface AccountManager {

    /**
     * Register new user to the system. If no librarian in the system first registered user will be one.
     * @param user to be register
     * @return registered user
     */
	public abstract User register(User user);

    /**
     * Looking for user? Here's your solution
     * @param username - this is ID of user
     * @return User user
     */
    public User find(String username);

}