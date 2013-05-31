package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.jboss.book.persistence.entity.User;

public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext(name = "PU")
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
	public boolean authenticate(User user) {
		User foundUser = em.find(User.class, user.getUsername());
		if (foundUser == null)
			return false;
		return user.getPassword().equals(user.getPassword());
	}

}
