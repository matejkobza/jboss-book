package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.jboss.book.persistence.entity.User;

public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext(unitName="PU")
	private EntityManager em;
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public User createUser(User user) {
		em.persist(user);
		em.flush();
		return user;
	}

	@Override
	public User updateUser(User user) {
		// TODO What if user can't be found?
		User dbUser = findUserByUsername(user.getUsername());
		dbUser.setName(user.getName());
		dbUser.setPassword(user.getPassword());
		// TODO Is user role updatable?
		dbUser.setUserRole(user.getUserRole());
		em.persist(dbUser);
		return dbUser;
	}

	@Override
	public void deleteUser(User user) {
		
		User dbUser = findUserByUsername(user.getUsername());
		em.remove(dbUser);
	}

	@Override
	public User findUserByUsername(String username) {
		return em.find(User.class, username);
	}

	@Override
	public List<User> findAllUsers() {
		return em.createQuery("SELECT u FROM User u").getResultList();
	}

    @Override
    public void deleteUserByUsername(String username) {
        em.remove(em.find(User.class, username));
    }

    /*@Override
    public List<User> searchUsers(String key) {
        //Query q = em.createQuery("SELECT u FROM User u WHERE u.name LIKE :X");
        //q.setParameter("X", "%" + key + "%");
        //return q.getResultList();
        return findAllUsers();
    } */
}
