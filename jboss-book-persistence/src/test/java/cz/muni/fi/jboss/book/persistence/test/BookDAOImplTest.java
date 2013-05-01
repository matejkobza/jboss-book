package cz.muni.fi.jboss.book.persistence.test;

import cz.muni.fi.jboss.book.persistence.dao.BookDAOImpl;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import javax.ejb.Local;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author Eduard Tomek
 */
@Local
public class BookDAOImplTest {
    
    
    BookDAOImpl bDao;
    
    Book testBook1;
    
    
    @Before
    public void setUp(){
        bDao = new BookDAOImpl();
        bDao.setEm(Persistence.createEntityManagerFactory("test-hibernate").createEntityManager());
        testBook1 = createTestBook1();
    }
    
    private Book createTestBook1(){
        Book book = new Book();
        book.setAuthor("Test author1");
        book.setISBN(1000l);
        book.setTitle("Test bookname1");
        book.setPages(100);
        book.setPublisher("Test publisher1");
        return book;
    }
    
    @Test
    public void testCreateNullBook(){
        try{
            bDao.createBook(null);
        }catch(NullPointerException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testCreateBookWithNullFields(){
        Book book = new Book();
        try{
           bDao.createBook(book);
        }catch (IllegalArgumentException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testCreateBookWithNullISBN(){
        testBook1.setISBN(null);
        try{
           bDao.createBook(testBook1);
        }catch (IllegalArgumentException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testCreateBookWithNegativeISBN(){
        testBook1.setISBN(Long.MIN_VALUE);
        try{
           bDao.createBook(testBook1);
        }catch (IllegalArgumentException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testCreateBookWithNullPages(){
        testBook1.setPages(null);
        try{
           bDao.createBook(testBook1);
        }catch (IllegalArgumentException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testCreateBookWithNegativePages(){
        testBook1.setPages(Integer.MIN_VALUE);
        try{
           bDao.createBook(testBook1);
        }catch (IllegalArgumentException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testCreateBook(){
        Book result = bDao.createBook(testBook1);
        assertNotNull(result.getId());
        assertTrue(result.getId() > 0);
        assertEquals(testBook1.getAuthor(), result.getAuthor());
        assertEquals(testBook1.getISBN(), result.getISBN());
        assertEquals(testBook1.getPages(), result.getPages());
        assertEquals(testBook1.getPublisher(), result.getPublisher());
        assertEquals(testBook1.getTitle(), result.getTitle());
    }
    
    @Test
    public void testUpdateBookWithNull(){
        bDao.createBook(testBook1);
        try{
            bDao.updateBook(null);
        }catch(NullPointerException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testUpdateBookWithNullISBN(){
        bDao.createBook(testBook1);
        testBook1.setISBN(null);
        try{
            bDao.updateBook(testBook1);
        }catch(NullPointerException ex){
            assertTrue(true);
        }
    }
    
    @Test
    public void testUpdateBookWithNegativeISBN(){
        bDao.createBook(testBook1);
        testBook1.setISBN(Long.MIN_VALUE);
        try{
            bDao.updateBook(testBook1);
        }catch(NullPointerException ex){
            assertTrue(true);
        }
    }
    
   @Test
    public void testUpdateBookWithNegativePages(){
        bDao.createBook(testBook1);
        testBook1.setPages(Integer.MIN_VALUE);
        try{
            bDao.updateBook(testBook1);
        }catch(NullPointerException ex){
            assertTrue(true);
        }
    }
    
   @Test
    public void testUpdateBookWithNullPages(){
        bDao.createBook(testBook1);
        testBook1.setPages(null);
        try{
            bDao.updateBook(testBook1);
        }catch(NullPointerException ex){
            assertTrue(true);
        }
    }
   
   @Test
   public void testDeleteBookWithNull(){
       try{
           bDao.deleteBook(null);
       }catch (NullPointerException ex){
           assertTrue(true);
       }
   }

   @Test
   public void testDeleteBook(){
       bDao.createBook(testBook1);
       bDao.deleteBook(testBook1);
       assertTrue(bDao.findAllBooks().isEmpty());
   }
}
