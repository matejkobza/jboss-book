package cz.muni.fi.jboss.book.web.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

	private String authorFirstName;
	private String authorSurname;

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
	
	public void searchBookByAuthor() {
		String firstName = this.authorFirstName == null ? "" : this.authorFirstName.trim();
		String surname = this.authorSurname == null ? "" : this.authorSurname.trim();
		this.searchBookResults = bookManager.findBookByAuthor(firstName, surname);
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

	public List<Book> getListBooks() {
		return bookManager.findAllBooks();
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorSurname() {
		return authorSurname;
	}

	public void setAuthorSurname(String authorSurname) {
		this.authorSurname = authorSurname;
	}

}
