package cz.muni.fi.jboss.book.persistence.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author matejkobza
 */
public class EntityManagerFactoryTest {

  private EntityManager entityManager;

  @Before
  public void setUp() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-hibernate");
    entityManager = emf.createEntityManager();
  }
  
  @Test
  public void checkEntityManager() {
    assertTrue(entityManager.isOpen());
  }
}