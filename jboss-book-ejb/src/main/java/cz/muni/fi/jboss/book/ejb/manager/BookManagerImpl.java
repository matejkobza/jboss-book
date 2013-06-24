package cz.muni.fi.jboss.book.ejb.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.ejb3.annotation.Clustered;

import cz.muni.fi.jboss.book.ejb.entities.BookCopyWithDetails;
import cz.muni.fi.jboss.book.ejb.util.ReservationUtils;
import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.dao.AuthorDAO;
import cz.muni.fi.jboss.book.persistence.dao.BookCopyDAO;
import cz.muni.fi.jboss.book.persistence.dao.BookDAO;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;

@Dependent
@Named("bookManager")
@Stateless
@Remote
@Clustered
public class BookManagerImpl implements BookManager {

	@Inject
	private BookDAO bookDao;

	@Inject
	private BookCopyDAO bookCopyDao;

	@Inject
	private AuthorDAO authorDao;

	@EJB
	private ReservationManager reservationManager;

	private boolean authorExists(Author author) {
		// TODO - inefficient - fix
		List<Author> authors = authorDao.findAllAuthors();
		for (Author oneAuthor : authors) {
			if (author.getFirstName().equals(oneAuthor.getFirstName())
					&& author.getSurname().equals(oneAuthor.getSurname()))
				return true;
		}
		
		return false;
	}

	@Override
	public Book addBook(Book book) {
		Author author = book.getAuthor();
		if (!authorExists(author))
			authorDao.createAuthor(author);
		return bookDao.createBook(book);
	}

	@Override
	public Book updateBook(Book book) {
		return bookDao.updateBook(book);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookDao.deleteBook(bookDao.findBookById(bookId));
	}

	@Override
	public Book findBookById(Long id) {
		return bookDao.findBookById(id);
	}

	@Override
	public List<Book> findBookByISBN(Long ISBN) {
		return bookDao.findBookByISBN(ISBN);
	}

	@Override
	public List<Book> findBookByTitle(String title) {
		return bookDao.findBookByTitle(title);
	}

	@Override
	public List<Book> findBookByTitlePart(String titlePart) {
		return bookDao.findBookByTitlePart(titlePart);
	}

	@Override
	public List<Book> findBookByAuthor(String firstName, String surname) {
		Author author = new Author();
		author.setFirstName(firstName);
		author.setSurname(surname);

		return bookDao.findBookByAuthor2(author);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookDao.findAllBooks();
	}

	@Override
	public BookCopy addBookCopy(Long bookId, BookCopy bookCopy) {
		bookCopy.setBook(bookDao.findBookById(bookId));
		return bookCopyDao.createBookCopy(bookCopy);
	}

	@Override
	public void deleteBookCopy(Long bookCopyId) {
		bookCopyDao.deleteBookCopy(bookCopyDao.findBookCopyById(bookCopyId));
	}

	@Override
	public BookCopy updateBookCopy(BookCopy bookCopy) {
		return bookCopyDao.updateBookCopy(bookCopy);
	}

	@Override
	public List<BookCopy> getBookCopiesByBookId(Long bookId) {
		return bookCopyDao.findBookCopyByBook(findBookById(bookId));
	}

	@Override
	public List<BookCopyWithDetails> getBookCopiesWithDetailsByBookId(Long bookId) {
		List<BookCopy> bookCopies = getBookCopiesByBookId(bookId);
		List<BookCopyWithDetails> bookCopiesWithDetails = new ArrayList<BookCopyWithDetails>();
		for (BookCopy bookCopy : bookCopies) {
			boolean isAvailable = ReservationUtils.isAvailable(reservationManager, bookCopy);

			BookCopyWithDetails bookCopyWithDetails = new BookCopyWithDetails();
			bookCopyWithDetails.setBookCopy(bookCopy);
			bookCopyWithDetails.setAvailable(isAvailable);
			bookCopiesWithDetails.add(bookCopyWithDetails);
		}

		return bookCopiesWithDetails;
	}

}
