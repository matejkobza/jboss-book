package cz.muni.fi.jboss.book.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import cz.muni.fi.jboss.book.ejb.entities.BookCopyWithDetails;
import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.persistence.entity.Book;

@ManagedBean
@SessionScoped
public class SearchBookBean implements Serializable {

    @EJB(name = "BookManager")
    private BookManager bookManager;

    private String bookTitle;
    private Collection<Book> searchBookResults;
    private Book selectedBook;
    private List<BookCopyWithDetails> bookCopies;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Collection<Book> getSearchBookResults() {
        return searchBookResults;
    }

    public void setSearchBookResults(Collection<Book> searchBookResults) {
        this.searchBookResults = searchBookResults;
    }

    public void searchBookByTitlePart() {
        String title = bookTitle == null ? "" : bookTitle.trim();
        this.searchBookResults = bookManager.findBookByTitlePart(title);
    }

    public Book getBook() {
        return this.selectedBook;
    }

    public void selectBook(Long bookId) {
        this.selectedBook = bookManager.findBookById(bookId);
        this.bookCopies = bookManager.getBookCopiesWithDetailsByBookId(bookId);
    }

    public List<BookCopyWithDetails> getBookCopies() {
        return this.bookCopies;
    }

    //@TODO implement
    public void reserveBook(Long bookCopyId) {
        FacesContext.getCurrentInstance().addMessage("Info", new FacesMessage("some message about booked book"));
    }

}
