package cz.muni.fi.jboss.book.persistence.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.dao.UserDAOImpl;
import cz.muni.fi.jboss.book.persistence.entity.User;


public class UserDAOImplTest {
	private UserDAOImpl uDao;
	private User user;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
	private EntityManager em;	
	
	@Before
	public void setUp(){
		uDao = new UserDAOImpl();
		em = emf.createEntityManager();
		user = createTestUser1();
	}
	
	private User createTestUser1(){
		User user = new User();
		user.setName("Michael Jackson");
		user.setPassword("###HashPass####");
		user.setUsername("mJackson");
		user.setUserRole(UserRole.MANAGER);
		return user;
	}
	
	private void simulateBeginTransaction() {
		em = emf.createEntityManager();
		uDao.setEm(em);
		em.getTransaction().begin();
	}

	private void simulateEndTransaction() {
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testCreateUser(){
		simulateBeginTransaction();
		User returnedUser = uDao.createUser(user);
		simulateEndTransaction();
		
		assertEquals(user.getName(), returnedUser.getName());
		assertEquals(user.getPassword(), returnedUser.getPassword());
		assertEquals(user.getUsername(), returnedUser.getUsername());
		assertEquals(user.getUserRole(), returnedUser.getUserRole());
		
		//need to clean up, because of uniqueness of username
		simulateBeginTransaction();
		uDao.deleteUser(user);
		simulateEndTransaction();
	
		
	}
	
	@Test
	public void testUpdateUser(){
		simulateBeginTransaction();
		user = uDao.createUser(user);
		simulateEndTransaction();
		user.setName("Some different test name");
		user.setPassword("newPassswwwd");
		//no, if i change username i change pk and there is nothing to update
		//user.setUsername("Is it ok to change username? No!");
		user.setUserRole(UserRole.READER);//Is it OK just change user role?
		
		simulateBeginTransaction();
		User returnedUser = uDao.updateUser(user);
		simulateEndTransaction();
		
		assertEquals(user.getName(), returnedUser.getName());
		assertEquals(user.getPassword(), returnedUser.getPassword());
		assertEquals(user.getUsername(), returnedUser.getUsername());
		assertEquals(user.getUserRole(), returnedUser.getUserRole());
		
		//need to clean up, because of uniqueness of username
		simulateBeginTransaction();
		uDao.deleteUser(user);
		simulateEndTransaction();
	}
	
	@Test
	public void testFindUsers(){
		simulateBeginTransaction();
		User user1 = uDao.createUser(user);
		simulateEndTransaction();
		User user2 = new User();
		user2.setUsername("SomeOtherUsername");
		user2.setName("Some  name");
		user2.setPassword("newPass");
		user2.setUserRole(UserRole.LIBARIAN);
		simulateBeginTransaction();
		user2 = uDao.createUser(user2);
		simulateEndTransaction();
		
		
		simulateBeginTransaction();
		User foundUser = uDao.findUserByUsername(user2.getUsername());
		simulateEndTransaction();
		
		assertEquals(user2.getName(), foundUser.getName());
		assertEquals(user2.getPassword(), foundUser.getPassword());
		assertEquals(user2.getUsername(), foundUser.getUsername());
		assertEquals(user2.getUserRole(), foundUser.getUserRole());
		
		simulateBeginTransaction();
		List<User> foundUsers = uDao.findAllUsers();
		simulateEndTransaction();
		assertTrue(foundUsers.contains(user1));
		assertTrue(foundUsers.contains(user2));
		assertTrue(foundUsers.size() == 2);
		
		//need to clean up, because of uniqueness of username
		simulateBeginTransaction();
		uDao.deleteUser(user1);
		simulateBeginTransaction();
		/*
		simulateEndTransaction();
		uDao.deleteUser(user2);
		simulateEndTransaction();
		*/
	}
}

