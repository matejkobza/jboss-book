package cz.muni.fi.jboss.book.ejb.manager;

import cz.muni.fi.jboss.book.persistence.dao.AuthorDAO;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Dependent
@Named("authorManager")
@Stateless
@Remote
@Clustered

public class AuthorManagerImpl implements AuthorManager {

    @Inject
    private AuthorDAO authorDAO;

    @Override
    public void create(Author author) {
        authorDAO.createAuthor(author);
    }

    @Override
    public void delete(Author author) {
        authorDAO.deleteAuthor(author);
    }

    @Override
    public void update(Author author) {
        authorDAO.deleteAuthor(author);
    }

    @Override
    public Author find(Long id) {
        return authorDAO.findAuthorById(id);
    }

    @Override
    public List<Author> findByName(String key) {
        return authorDAO.searchByName(key);
    }
}
