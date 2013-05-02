package cz.muni.fi.library.ejb.managers;

import cz.muni.fi.jboss.book.persistence.entity.Book;

public interface BookManager {

	public abstract Book addBook(Book book);

}