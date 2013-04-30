/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.library.dao;

import cz.muni.fi.library.entity.Librarian;
import cz.muni.fi.library.entity.Manager;
import java.util.List;

/**
 *
 * @author Eduard Tomek
 */
public interface ManagerDAO {
    
    /**
     * Create manager
     * 
     * @param manager
     * @return created manager
     * @throws NullPointerException if manager is null
     * @throws IllegalArgumentException if manager with given name already exists
     */
    Manager createManager(Manager manager);
    
    
    /**
     * Update manager
     * 
     * @param manager
     * @return updated manager
     * @throws NullPointerException if manager is null
     * @throws IllegalArgumentException if manager with given name already exists
     */
    Manager updateManager(Manager manager);
    
    /**
     * Delete manager
     * 
     * @param manager
     * @throws NullPointerException if manager is null
     */
    void deleteManager(Manager manager);
    
    /**
     * Find manager by id
     * 
     * @param id
     * @return manager with given id
     * @throws NullPointerException if id is null
     */
    Manager findManagerById(Long id);
    
    /**
     * Find manager by username
     * 
     * @param username
     * @return manager with given username
     * @throws NullPointerException if username is null
     */
    Manager findManagerByUsername(String username);
    
    /**
     * Find all managers
     * 
     * @return List of all managers
     */
    List<Manager> findAllManagers();
    
}
