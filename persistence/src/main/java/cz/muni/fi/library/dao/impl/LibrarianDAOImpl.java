/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.library.dao.impl;

import cz.muni.fi.library.dao.LibrarianDAO;
import cz.muni.fi.library.entity.Librarian;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class LibrarianDAOImpl implements LibrarianDAO {

    @PersistenceContext
    EntityManager em;

    @Override
    public Librarian createLibrarian(Librarian librarian) {
        if (librarian == null) {
            throw new NullPointerException("librarian is null");
        }
        //test if librarian with this username wasn't already created
        if (findLibrarianByUsername(librarian.getUsername()) != null) {
            throw new IllegalArgumentException("librarian with this username already exists");
        }
        em.persist(librarian);
        em.flush();
        return librarian;
    }

    @Override
    public Librarian updateLibrarian(Librarian librarian) {
        if (librarian == null) {
            throw new NullPointerException("librarian is null");
        }
        //test if librarian with this username wasn't already created
        if (findLibrarianByUsername(librarian.getUsername()) != null) {
            throw new IllegalArgumentException("librarian with this username already exists");
        }
        Librarian dbLibrarian = em.find(Librarian.class, librarian.getId());
        dbLibrarian.setHireDay(librarian.getHireDay());
        dbLibrarian.setName(librarian.getName());
        dbLibrarian.setPassword(librarian.getPassword());
        dbLibrarian.setUsername(librarian.getUsername());
        em.persist(dbLibrarian);
        em.flush();
        return dbLibrarian;
    }

    @Override
    public void deleteLibrarian(Librarian librarian) {
        if (librarian == null) {
            throw new NullPointerException("librarian is null");
        }
        em.remove(librarian);
    }

    @Override
    public Librarian findLibrarianById(Long id) {
        if (id == null) {
            throw new NullPointerException("id is null");
        }
        return em.find(Librarian.class, id);
    }

    @Override
    public Librarian findLibrarianByUsername(String username) {
        if (username == null) {
            throw new NullPointerException("username is null");
        }
        Librarian result = null;
        try {
            result = (Librarian) em.createQuery("SELECT u FROM User u "
                    + "WHERE u.username == :username").getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return result;
    }

    @Override
    public List<Librarian> findAllLibrarians() {
        return em.createQuery("SELECT u FROM User u").getResultList();
    }
}
