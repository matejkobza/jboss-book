package cz.muni.fi.jboss.book.ejb.manager;

import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.dao.UserDAO;
import cz.muni.fi.jboss.book.persistence.entity.User;
import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Dependent
@Named("userManager")
@Stateless
@Remote
@Clustered
public class UserManagerImpl implements UserManager {

    @Inject
    private UserDAO userDAO;

    @Override
    public List<User> getUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public List<User> getReaders() {
        List<User> users = new ArrayList<>();
        for (User user : userDAO.findAllUsers()) {
            if (user.getUserRole() == UserRole.READER) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public List<User> getLibrarians() {
        List<User> users = new ArrayList<>();
        for(User user : userDAO.findAllUsers()) {
            if(user.getUserRole() == UserRole.LIBRARIAN) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userDAO.deleteUserByUsername(username);
    }

    @Override
    public List<User> searchUsers(String key) {
        return userDAO.searchUsers(key);
    }

    @Override
    public void update(User user) {
        userDAO.updateUser(user);
    }
}
