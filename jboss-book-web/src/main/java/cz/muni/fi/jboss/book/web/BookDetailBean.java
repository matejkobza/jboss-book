package cz.muni.fi.jboss.book.web;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import cz.muni.fi.jboss.book.ejb.entities.BookCopyWithDetails;
import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.persistence.entity.Book;

@ManagedBean
@ViewScoped
public class BookDetailBean {

	@EJB(name = "BookManager")
	private BookManager bookManager;

	private Long bookId;
	private Book book;
	private List<BookCopyWithDetails> bookCopies;

	public void onPageLoad() {
		try {
			String bookIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("id");
			this.bookId = Long.parseLong(bookIdStr);
			this.book = bookManager.findBookById(this.bookId);
			this.bookCopies = bookManager.getBookCopiesWithDetailsByBookId(this.bookId);
		} catch (NumberFormatException e) {
		}
	}

	public Book getBook() {
		return book;
	}

	public List<BookCopyWithDetails> getBookCopies() {
		return bookCopies;
	}

}
