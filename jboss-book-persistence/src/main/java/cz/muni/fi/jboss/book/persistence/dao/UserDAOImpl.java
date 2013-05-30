package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.jboss.book.persistence.entity.User;

public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext(name = "PU")
	private EntityManager em;
	
	@Override
	public User createUser(User user) {
		
		
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
