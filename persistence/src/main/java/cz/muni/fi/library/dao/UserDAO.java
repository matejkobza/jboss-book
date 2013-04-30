/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.library.dao;

import cz.muni.fi.library.entity.Reader;
import cz.muni.fi.library.entity.User;
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
}
