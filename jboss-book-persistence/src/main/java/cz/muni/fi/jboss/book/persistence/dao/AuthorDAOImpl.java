package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.jboss.book.persistence.entity.Author;
import cz.muni.fi.jboss.book.persistence.entity.Book;

public class AuthorDAOImpl implements AuthorDAO {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;


    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author createAuthor(Author author) {
        if (author == null) {
            throw new NullPointerException("author is null");
        }
        em.persist(author);
        em.flush();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        if (author == null) {
            throw new NullPointerException("author is null");
        }
        if (author.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        Author dbAuthor = em.find(Author.class, author.getId());
        dbAuthor.setBooks(author.getBooks());
        dbAuthor.setDescription(author.getDescription());
        dbAuthor.setFirstName(author.getFirstName());
        dbAuthor.setSurname(author.getSurname());
        em.persist(dbAuthor);
        em.flush();
        return dbAuthor;
    }

    @Override
    public void deleteAuthor(Author author) {
        if (author == null) {
            throw new NullPointerException("author is null");
        }
        if (author.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        Author dbAuthor = findAuthorById(author.getId());
        em.remove(dbAuthor);
        em.flush();
    }

    @Override
    public Author findAuthorById(Long id) {
        if (id == null) {
            throw new NullPointerException("id is null");
        }
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> findAllAuthors() {
        return em.createQuery("SELECT a FROM Author a").getResultList();
    }

    public List<Author> searchByName(String key) {
        return em.createQuery("SELECT a FROM Author a WHERE LOWER(firstname) LIKE LOWER(:x) OR LOWER(surname) LIKE LOWER(:y) OR (LOWER(firstname) || ' ' || LOWER(surname)) LIKE LOWER(:z)")
                .setParameter("x", "%" + key + "%")
                .setParameter("y", "%" + key + "%")
                .setParameter("z", "%" + key + "%").getResultList();
    }

}
