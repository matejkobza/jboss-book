package cz.muni.fi.jboss.book.ejb.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;

@Singleton
@Startup
public class RecordInitializer {

	@EJB
	private AccountManager accountManager;

	@EJB
	private BookManager bookManager;

	@EJB
	private ReservationManager reservationManager;

	private User reader;
	private User reader2;

	private Book book;
	private static final int BOOK_COPIES_COUNT = 4;
	private List<BookCopy> bookCopies = new ArrayList<BookCopy>();

	private void createUsers() {
		// create one reader
		reader = new User();
		reader.setName("Mr. Reader");
		reader.setUsername("reader");
		reader.setPassword("reader");
		reader.setUserRole(UserRole.READER);

		accountManager.register(reader);

		reader2 = new User();
		reader2.setName("Mrs. Reader");
		reader2.setUsername("readera");
		reader2.setPassword("readera");
		reader2.setUserRole(UserRole.READER);

		accountManager.register(reader2);
	}

	private void createBookCopies(Book book, int copiesCount, List<BookCopy> bookCopies) {
		for (int i = 0; i < copiesCount; i++) {
			BookCopy bookCopy = new BookCopy();
			bookCopy.setBook(book);
			bookCopy.setPurchaseDate(new Date());
			bookCopy = bookManager.addBookCopy(book.getId(), bookCopy);
			bookCopies.add(bookCopy);
		}
	}

	private void createBooks() {
		book = new Book();
		book.setAuthor(null);
		book.setISBN(131103628L);
		book.setPages(274);
		book.setPublisher("Prentice Hall");
		book.setTitle("C Programming Language (2nd Edition)");

		book = bookManager.addBook(book);

		createBookCopies(book, BOOK_COPIES_COUNT, this.bookCopies);
	}

	private void reserveBooks() {
		// one bookcopy has already been returned, one is lent, one is reserved
		
		BookCopy bookCopy = this.bookCopies.get(0);

		BookCopyReservation reservation = reservationManager.reserveBook(bookCopy, this.reader);
		reservationManager.prepareBook(reservation.getId());
		reservationManager.lendBook(reservation.getId());
		reservationManager.returnBook(reservation.getId());

		BookCopy bookCopy2 = this.bookCopies.get(1);
		BookCopyReservation reservation2 = reservationManager.reserveBook(bookCopy2, this.reader);
		reservationManager.prepareBook(reservation2.getId());
		reservationManager.lendBook(reservation2.getId());
		
		BookCopy bookCopy3 = this.bookCopies.get(2);
		@SuppressWarnings("unused")
		BookCopyReservation reservation3 = reservationManager.reserveBook(bookCopy3, this.reader);
	}

	@PostConstruct
	public void create() {
		//createUsers();
		//createBooks();
		//reserveBooks();
	}

}
