package cz.muni.fi.jboss.book.ejb.manager;

import cz.muni.fi.jboss.book.persistence.entity.Author;

import java.util.List;

public interface AuthorManager {

    public void create(Author author);

    public void delete(Author author);

    public void update(Author author);

    public Author find(Long id);

    public List<Author> findByName(String key);

}
