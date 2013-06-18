package cz.muni.fi.jboss.book.ejb.util;

import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;
import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;

public class ReservationUtils {
	
	public static boolean isAvailable(ReservationManager reservationManager, BookCopy bookCopy) {
		boolean isAvailable = true;
		for (BookCopyReservation reservation : reservationManager.getBookCopyReservations(bookCopy.getId())) {
			if (!reservation.getReservationState().equals(ReservationState.RETURNED))
				isAvailable = false;
		}
		
		return isAvailable;
	}

}
