package cz.muni.fi.jboss.book.ejb.entities;

import java.io.Serializable;

import cz.muni.fi.jboss.book.persistence.entity.BookCopy;

public class BookCopyWithDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BookCopy bookCopy;
	private boolean available = false;

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
