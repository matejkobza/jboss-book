package cz.muni.fi.jboss.book.ejb.manager;

import cz.muni.fi.jboss.book.persistence.entity.User;

import java.util.List;

public interface UserManager {

    public List<User> getUsers();

    public List<User> getReaders();

    public List<User> getLibrarians();

    public void deleteUser(User user);

    public void deleteUserByUsername(String username);

    public List<User> searchUsers(String key);

    public void update(User user);

}
