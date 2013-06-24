package cz.muni.fi.jboss.book.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import cz.muni.fi.jboss.book.ejb.manager.UserManager;
import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.core.WebBeanFactory;

import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;
import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;

@ManagedBean
@ViewScoped
public class ReservationsBean implements Serializable {

    @EJB(name = "ReservationManager")
    private ReservationManager reservationManager;

    @EJB(name = "UserManager")
    private UserManager userManager;

    private String readerName;
    private User reader;

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public List<String> complete(String query) {
        List<String> results = new ArrayList<>();

        for (User user : userManager.searchUsers(query)) {
            if (user.getUserRole() == UserRole.READER) {
                results.add(user.getName());
            }
        }

        return results;
    }

    public void searchUser() {
        if (this.readerName != null) {
            List<User> users = userManager.searchUsers(this.readerName);
            if (users.size() == 1) {
                this.reader = users.get(0);
            } else {
                WebApplication.getReference().addErrorMessage("Reservations service", "more than one possibility for user. Please, be more specific.");
            }
        }
    }

    public boolean isUserSelected() {
        return this.reader != null;
    }

    public List<BookCopyReservation> getReservations() {
        return reservationManager.getAllBookCopyReservations(false);
    }

    public List<BookCopyReservation> getActiveReservations() {
        List<BookCopyReservation> activeReservations = new ArrayList<>();
        for (BookCopyReservation bookCopyReservation : getReservations()) {
            if (bookCopyReservation.getReservationState() == ReservationState.NEW) {
                activeReservations.add(bookCopyReservation);
            }
        }
        return activeReservations;
    }


    public String getLocalizedReservationState(ReservationState key) {
        if (WebApplication.getReference().getResourceBundle().containsKey(key.toString())) {
            return WebApplication.getReference().getResourceBundle().getString(key.toString());
        }
        return key.toString();
    }

    public void prepareBook(Long reservationId) {
        reservationManager.prepareBook(reservationId);
    }

    public void lendBook(Long reservationId) {
        reservationManager.lendBook(reservationId);
    }

    public void returnBook(Long reservationId) {
        reservationManager.returnBook(reservationId);
    }

    public void cancelReservation(Long reservationId) {
        reservationManager.cancelReservation(reservationId);
    }


// Pragma mark - selected reader

    public List<BookCopyReservation> getReaderReservations() {
        List<BookCopyReservation> reservations = new ArrayList<>();
        reservations.addAll(reservationManager.getBookCopyReservations(this.reader, ReservationState.READY));
        reservations.addAll(reservationManager.getBookCopyReservations(this.reader, ReservationState.NEW));
        return reservations;
    }

    public List<BookCopyReservation> getReaderBorrowings() {
        return reservationManager.getBookCopyReservations(this.reader, ReservationState.LENT);
    }


// Pragma mark - current logged in user

    public List<BookCopyReservation> getUserReservations() {
        return reservationManager.getBookCopyReservations(WebBeanFactory.getIdentityBean().getUser(), ReservationState.NEW);
    }

    public List<BookCopyReservation> getUserReservationsHistory() {
        List<BookCopyReservation> reservations = new ArrayList<>();
        for (BookCopyReservation reservation : reservationManager.getAllBookCopyReservations(true)) {
            if (reservation.getUser().equals(WebBeanFactory.getIdentityBean().getUser())) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public List<BookCopyReservation> getUserBorrowings() {
        return reservationManager.getBookCopyReservations(WebBeanFactory.getIdentityBean().getUser(), ReservationState.LENT);
    }

}
