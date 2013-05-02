package cz.muni.fi.library.ejb.managers;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.jboss.book.persistence.dao.BookDAO;
import cz.muni.fi.jboss.book.persistence.entity.Book;

@Dependent
@Named
@Stateless
@Remote
public class BookManagerImpl implements BookManager {
	
	@Inject
	private BookDAO bookDao;

	/* (non-Javadoc)
	 * @see cz.muni.fi.library.ejb.managers.BookManager#addBook(cz.muni.fi.jboss.book.persistence.entity.Book)
	 */
	@Override
	public Book addBook(Book book) {
		return bookDao.createBook(book);
	}

}
