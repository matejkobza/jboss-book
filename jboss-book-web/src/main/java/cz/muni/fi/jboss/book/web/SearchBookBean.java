package cz.muni.fi.jboss.book.web;

import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.persistence.entity.Book;

@ManagedBean
@ViewScoped
public class SearchBookBean {
	
	@EJB(name = "BookManager")
	private BookManager bookManager;

	private String bookTitle;
	private Collection<Book> searchBookResults;

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
	
	public void searchBook() {
		String title = bookTitle == null ? "" : bookTitle.trim();
		this.searchBookResults = bookManager.findBookByTitle(title);
	}

}
