package cz.muni.fi.jboss.book.ejb.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.ejb3.annotation.Clustered;

import cz.muni.fi.jboss.book.ejb.util.ReservationUtils;
import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.dao.BookCopyDAO;
import cz.muni.fi.jboss.book.persistence.dao.BookCopyReservationDAO;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;

@Dependent
@Named("reservationManager")
@Stateless
@Remote
@Clustered
public class ReservationManagerImpl implements ReservationManager {

	@Inject
	private BookCopyReservationDAO bookCopyReservationDao;

	@Inject
	private BookCopyDAO bookCopyDao;

	@Override
	public BookCopyReservation reserveBook(BookCopy bookCopy, User reader) {
		// already reserved
		if (!ReservationUtils.isAvailable(this, bookCopy))
			return null;

		BookCopyReservation reservation = new BookCopyReservation();
		reservation.setBookCopy(bookCopy);
		reservation.setReservationState(ReservationState.NEW);
		reservation.setUser(reader);
		reservation.setCreated(new Date());

		return bookCopyReservationDao.createBookCopyReservation(reservation);
	}

	@Override
	public BookCopyReservation reserveBook(Long bookCopyId, User user) {
		return reserveBook(bookCopyDao.findBookCopyById(bookCopyId), user);
	}

	private void checkReservationState(BookCopyReservation reservation, ReservationState requiredState) {
		if (!reservation.getReservationState().equals(requiredState))
			// TODO - throw checked exception?
			throw new IllegalArgumentException("The state of the reservation must be " + requiredState.toString());
	}

	private BookCopyReservation getReservation(Long reservationId) {
		return bookCopyReservationDao.findBookCopyReservationById(reservationId);
	}

	@Override
	public void prepareBook(Long reservationId) {
		BookCopyReservation reservation = getReservation(reservationId);
		checkReservationState(reservation, ReservationState.NEW);
		reservation.setReservationState(ReservationState.READY);
		bookCopyReservationDao.updateBookCopyReservation(reservation);
	}

	@Override
	public void lendBook(Long reservationId) {
		BookCopyReservation reservation = getReservation(reservationId);
		checkReservationState(reservation, ReservationState.READY);
		reservation.setReservationState(ReservationState.LENT);
		bookCopyReservationDao.updateBookCopyReservation(reservation);
	}

	@Override
	public void returnBook(Long reservationId) {
		BookCopyReservation reservation = getReservation(reservationId);
		checkReservationState(reservation, ReservationState.LENT);
		reservation.setReservationState(ReservationState.RETURNED);
		bookCopyReservationDao.updateBookCopyReservation(reservation);
	}

	@Override
	public List<BookCopyReservation> getBookCopyReservations(User reader, ReservationState state) {
		if (reader == null)
			return bookCopyReservationDao.findBookCopyReservationsByReservationState(state);
		else
			return bookCopyReservationDao.findBookCopyReservations(reader, state);
	}

	@Override
	public List<BookCopyReservation> getBookCopyReservations(Long bookCopyId) {
		BookCopy bookCopy = new BookCopy();
		bookCopy.setId(bookCopyId);
		return bookCopyReservationDao.findBookCopyReservationsByBookCopy(bookCopy);
	}

	@Override
	public List<BookCopyReservation> getAllBookCopyReservations(boolean includeReturned) {
		List<BookCopyReservation> reservations = bookCopyReservationDao.findAllBookCopyReservations();
		if (includeReturned)
			return reservations;
		else {
			ArrayList<BookCopyReservation> filteredList = new ArrayList<BookCopyReservation>();
			for (BookCopyReservation reservation : reservations) {
				if (!(reservation.getReservationState().equals(ReservationState.RETURNED)))
					filteredList.add(reservation);
			}
			return filteredList;
		}
	}

}
