package cz.muni.fi.jboss.book.persistence.dao;


import cz.muni.fi.jboss.book.persistence.entity.User;
import java.util.List;

/**
 *
 * @author Eduard Tomek
 */
public interface UserDAO {

  User createUser(User user);

  User updateUser(User user);

  void deleteUser(User user);

  User findUserById(Long id);

  User findUserByUsername(String username);

  List<User> findAllUsers();
  
  boolean authenticate(User user);
}
