package cz.muni.fi.jboss.book.persistence.dao.impl;

import cz.muni.fi.jboss.book.persistence.dao.BookCopyReservationDAO;
import cz.muni.fi.library.entity.BookCopy;
import cz.muni.fi.library.entity.BookCopyReservation;
import cz.muni.fi.library.entity.User;
import cz.muni.fi.library.enums.ReservationState;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eduard Tomek
 */
public class BookCopyReservationDAOImpl implements BookCopyReservationDAO {

  @PersistenceContext
  private EntityManager em;

  public EntityManager getEm() {
    return em;
  }

  public void setEm(EntityManager em) {
    this.em = em;
  }

  @Override
  public BookCopyReservation createBookCopyReservation(BookCopyReservation bookCopyReservation) {
    if (bookCopyReservation == null) {
      throw new NullPointerException("bookCopyReservation is null");
    }
    if (bookCopyReservation.getBookCopy() == null
            || bookCopyReservation.getUser() == null
            || bookCopyReservation.getReservationState() == null) {
      throw new IllegalArgumentException("book copy, user or reservation state is null");
    }
    em.persist(bookCopyReservation);
    em.flush();
    return bookCopyReservation;
  }

  @Override
  public BookCopyReservation updateBookCopyReservation(BookCopyReservation bookCopyReservation) {
    if (bookCopyReservation == null) {
      throw new NullPointerException("bookCopyReservation is null");
    }
    if (bookCopyReservation.getBookCopy() == null
            || bookCopyReservation.getUser() == null
            || bookCopyReservation.getReservationState() == null) {
      throw new IllegalArgumentException("book copy, user or reservation state is null");
    }
    BookCopyReservation dbBCR = em.find(BookCopyReservation.class, bookCopyReservation.getId());
    dbBCR.setReservationState(bookCopyReservation.getReservationState());
    em.persist(dbBCR);
    em.flush();
    return dbBCR;
  }

  @Override
  public void deleteBookCopyReservation(BookCopyReservation bookCopyReservation) {
    if (bookCopyReservation == null) {
      throw new NullPointerException("bookCopyReservation is null");
    }
    em.remove(bookCopyReservation);
    em.flush();
  }

  @Override
  public BookCopyReservation findBookCopyReservationById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id is null");
    }
    return em.find(BookCopyReservation.class, id);
  }

  @Override
  public List<BookCopyReservation> findBookCopyReservationsByBookCopy(BookCopy bookCopy) {
    if (bookCopy == null) {
      throw new IllegalArgumentException("Id is null");
    }
    List<BookCopyReservation> reservations =
            em.createQuery(
            "SELECT b FROM BookCopyReservation b WHERE b.bookCopy == :bookCopy").getResultList();
    return reservations;
  }

  @Override
  public List<BookCopyReservation> findBookCopyReservationsByUser(User user) {
    if (user == null) {
      throw new NullPointerException("user is null");
    }
    List<BookCopyReservation> reservations =
            em.createQuery(
            "SELECT b FROM BookCopyReservation b WHERE b.user == :user").getResultList();
    return reservations;
  }

  @Override
  public List<BookCopyReservation> findBookCopyReservationsByReservationState(ReservationState rS) {
    if (rS == null) {
      throw new NullPointerException("reservation state is null");
    }
    List<BookCopyReservation> reservations =
            em.createQuery(
            "SELECT b FROM BookCopyReservation b WHERE b.reservationState == :rS").getResultList();
    return reservations;
  }

  @Override
  public List<BookCopyReservation> findAllBookCopyReservations() {
    return em.createQuery(
            "SELECT b FROM BookCopyReservation b").getResultList();
  }
}
