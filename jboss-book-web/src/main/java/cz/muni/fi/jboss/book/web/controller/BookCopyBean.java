package cz.muni.fi.jboss.book.web.controller;

import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.web.core.WebApplication;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class BookCopyBean implements Serializable {

    @EJB(name = "BookManager")
    private BookManager bookManager;

    @EJB(name = "ReservationManager")
    private ReservationManager reservationManager;

    private Book book;
    private BookCopy bookCopy = new BookCopy();

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public void save() {
        this.bookCopy.setBook(this.book);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.bookCopy.setPurchaseDate(sdf.parse(this.date));
            bookManager.addBookCopy(this.book.getId(), this.bookCopy);
        } catch (ParseException ex) {
            WebApplication.getReference().addErrorMessage("Unable to parse purchase date. Make sure it has the right format yyyy-MM-dd.");
        }

    }

    public void delete(Long bookCopyId) {
        List<BookCopyReservation> reservations = reservationManager.getBookCopyReservations(bookCopyId);
        if (reservations.isEmpty()) {
            bookManager.deleteBookCopy(bookCopyId);
        } else {
            WebApplication.getReference().addErrorMessage("Unable to delete book copy because it is resservered by someone.");
        }
    }

    public List<BookCopy> getBookCopies() {
        return bookManager.getBookCopiesByBookId(this.book.getId());
    }
}
