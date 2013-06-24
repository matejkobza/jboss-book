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

    private String authorName;
    private String authorSurname;
    private String authorDesc;
    private Long authorId;

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getAuthorDesc() {
        return authorDesc;
    }

    public void setAuthorDesc(String authorDesc) {
        this.authorDesc = authorDesc;
    }

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

    public void updateOrCreateAuthor() {
        if (this.authorId == null) {
            Author author = new Author();
            author.setFirstName(this.authorName);
            author.setSurname(this.authorSurname);
            author.setDescription(this.authorDesc);
            authorManager.create(author);
            WebApplication.getReference().addInfoMessage("Author service", "author created successfully.");
        } else {
            Author author = authorManager.find(this.authorId);
            author.setFirstName(this.authorName);
            author.setSurname(this.authorSurname);
            author.setDescription(this.authorDesc);
            authorManager.update(author);
            WebApplication.getReference().addInfoMessage("Author service", "author updated successfully.");
        }
    }

    public void updateOrCreateBook() {
        if (this.book.getId() == null) {
            List<Author> authors = authorManager.findByName(this.authorName);
            if (authors.size() == 1) {
                Author author = authors.get(0);
                this.book.setAuthor(author);
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

    public List<Author> getAuthors() {
        return authorManager.findAllAuthors();
    }

    public void selectBook(Book book) {
        this.book = book;
        //this.author = book.getAuthor();
        this.authorName = book.getAuthor().getFirstName() + " " + book.getAuthor().getSurname();
    }

    public void selectAuthor(Author author) {
        this.authorId = author.getId();
        this.authorName = author.getFirstName();
        this.authorSurname = author.getSurname();
        this.authorDesc = author.getDescription();
    }

    public void deleteBook(Long bookId) {
        bookManager.deleteBook(bookId);
        WebApplication.getReference().addInfoMessage("Book service", "book deleted successfully.");
    }

    //@TODO how should be this done?
    public void deleteAuthor(Long authorId) {
        authorManager.delete(authorManager.find(authorId));
        WebApplication.getReference().addInfoMessage("Author service", "author deleted successfully.");
    }

}
