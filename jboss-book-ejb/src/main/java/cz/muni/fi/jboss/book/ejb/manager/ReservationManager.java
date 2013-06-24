package cz.muni.fi.jboss.book.ejb.manager;

import java.util.List;

import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;

public interface ReservationManager {

	/**
	 * Used by Reader for book reservations
	 */
	public BookCopyReservation reserveBook(BookCopy bookCopy, User reader);
	
	public BookCopyReservation reserveBook(Long bookCopyId, User user);

	/**
	 * Used by Librarian when the reserved book is prepared
	 */
	public void prepareBook(Long reservationId);

	/**
	 * Used by Librarian when he's lending the book (or BookCopy to be precise)
	 */
	public void lendBook(Long reservationId);

	/**
	 * Used by Librarian when the Reader returns the book (BookCopy)
	 */
	public void returnBook(Long reservationId);

	/**
	 * Returns all BookCopyReservations with the specified state
	 * 
	 * @param reader
	 *            Limits the returned reservations to those with the specific
	 *            Reader. If null, no such filtering is done.
	 */
	public List<BookCopyReservation> getBookCopyReservations(User reader, ReservationState state);
	
	/**
	 * Returns all reservations related to this BookCopy
	 * 
	 * @param bookCopyId BookCopy ID
	 */
	public List<BookCopyReservation> getBookCopyReservations(Long bookCopyId);

    /**
     *
     * @param includeReturned includes returned books
     * @return
     */
	public List<BookCopyReservation> getAllBookCopyReservations(boolean includeReturned);

    public void cancelReservation(Long reservationId);

    public List<BookCopyReservation> getBookCopyReservations(ReservationState reservationState);

}
