package cz.muni.fi.jboss.book.persistence.dao.impl;

import cz.muni.fi.jboss.book.persistence.dao.ManagerDAO;
import cz.muni.fi.jboss.book.persistence.entity.Manager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class ManagerDAOImpl implements ManagerDAO {

  @PersistenceContext
  EntityManager em;

  @Override
  public Manager createManager(Manager manager) {
    if (manager == null) {
      throw new NullPointerException("manager is null");
    }
    if (findManagerByUsername(manager.getUsername()) != null) {
      throw new IllegalArgumentException("manager with this username already exists");
    }
    em.persist(manager);
    em.flush();
    return manager;
  }

  @Override
  public Manager updateManager(Manager manager) {
    if (manager == null) {
      throw new NullPointerException("manager is null");
    }
    if (findManagerByUsername(manager.getUsername()) != null) {
      throw new IllegalArgumentException("manager "
              + "with this username already exists");
    }
    Manager dbManager = em.find(Manager.class, manager.getId());
    dbManager.setName(manager.getName());
    dbManager.setPassword(manager.getPassword());
    dbManager.setUsername(manager.getUsername());
    dbManager.setSalary(manager.getSalary());
    em.persist(dbManager);
    em.flush();
    return dbManager;
  }

  @Override
  public void deleteManager(Manager manager) {
    if (manager == null) {
      throw new NullPointerException("manager is null");
    }
    em.remove(manager);
    em.flush();
  }

  @Override
  public Manager findManagerById(Long id) {
    if (id == null) {
      throw new NullPointerException("id is null");
    }
    return em.find(Manager.class, id);
  }

  @Override
  public Manager findManagerByUsername(String username) {
    if (username == null) {
      throw new NullPointerException("username is null");
    }
    Manager result;
    try {
      result = (Manager) em.createQuery("SELECT u FROM User u "
              + "WHERE u.username == :username").getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
    return result;
  }

  @Override
  public List<Manager> findAllManagers() {
    return em.createQuery("SELECT u FROM User u").getResultList();
  }
}
