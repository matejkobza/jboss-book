package cz.muni.fi.jboss.book.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;
import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.core.WebBeanFactory;

import java.util.List;

@ManagedBean
@ViewScoped
public class ReservationBean {

	@EJB(name = "ReservationManager")
	private ReservationManager reservationManager;

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

	public boolean reserve(Long bookCopyId) {
		User user = accountManager.find(WebBeanFactory.getIdentityBean().getUser().getUsername());
        if(user == null) {
            WebApplication.getReference().addErrorMessage("Reservation", "unable to reserve book. You need to login first.");
            return false;
        } else {
            BookCopyReservation r = reservationManager.reserveBook(bookCopyId, user);
            if(r == null) {
                WebApplication.getReference().addErrorMessage("Reservation", "reservation failed.");
                return false;
            } else {
                WebApplication.getReference().addInfoMessage("Reservation", "reservation successful.");
                return true;
            }
        }
	}

    public List<BookCopyReservation> getReservations() {
        throw new UnsupportedOperationException();
    }

    public List<BookCopyReservation> getBorrowedBooks() {
        throw new UnsupportedOperationException();
    }

}
