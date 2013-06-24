package cz.muni.fi.jboss.book.web.controller;

import cz.muni.fi.jboss.book.ejb.manager.AuthorManager;
import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;
import cz.muni.fi.jboss.book.web.core.WebApplication;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookBean implements Serializable {

    @EJB(name = "authorManager")
    private AuthorManager authorManager;

    @EJB(name = "bookManager")
    private BookManager bookManager;

    private Book book = new Book();
    private Author author = new Author();
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void createAuthor() {
        authorManager.create(this.author);
        WebApplication.getReference().addInfoMessage("Book service", "author created successfully.");
    }

    public void updateOrCreateBook() {
        if (this.book.getId() == null) {
            List<Author> authors = authorManager.findByName(this.authorName);
            if (authors.size() == 1) {
                this.author = authors.get(0);
                this.book.setAuthor(this.author);
                bookManager.addBook(this.book);
                WebApplication.getReference().addInfoMessage("Book service", "book created successfully.");
            } else {
                WebApplication.getReference().addErrorMessage("Book service", "multiple possibilities for author detected. Please be more specific");
            }
        } else {
            bookManager.updateBook(this.book);
            WebApplication.getReference().addInfoMessage("Book service", "book updated successfully.");
        }
    }

    public List<String> complete(String query) {
        List<String> results = new ArrayList<>();

        List<Author> authors = authorManager.findByName(query);

        for (Author author : authors) {
            results.add(author.getFirstName() + " " + author.getSurname());
        }

        return results;
    }

    public List<Book> getBooks() {
        return bookManager.findAllBooks();
    }
}
