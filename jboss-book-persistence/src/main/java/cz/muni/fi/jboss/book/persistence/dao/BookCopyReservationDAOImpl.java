package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;

/**
 *
 * @author Eduard Tomek
 */
public class BookCopyReservationDAOImpl implements BookCopyReservationDAO {

  @PersistenceContext(unitName="PU")
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
    		|| bookCopyReservation.getId() == null
            || bookCopyReservation.getReservationState() == null) {
      throw new IllegalArgumentException("book copy, user or reservation state is null");
    }
    BookCopyReservation dbBCR = em.find(BookCopyReservation.class, bookCopyReservation.getId());
    dbBCR.setReservationState(bookCopyReservation.getReservationState());
    dbBCR.setBookCopy(bookCopyReservation.getBookCopy());
    em.persist(dbBCR);
    em.flush();
    return dbBCR;
  }

  @Override
  public void deleteBookCopyReservation(BookCopyReservation bookCopyReservation) {
    if (bookCopyReservation == null) {
      throw new NullPointerException("bookCopyReservation is null");
    }
    if(bookCopyReservation.getId() == null){
    	throw new IllegalArgumentException("id is null");
    }
    bookCopyReservation = em.find(BookCopyReservation.class, bookCopyReservation.getId());
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
    return (List<BookCopyReservation>) em.createQuery(
            "SELECT b FROM BookCopyReservation b WHERE b.bookCopy = :bookCopy")
            .setParameter("bookCopy", bookCopy).getResultList();
  }

  @Override
  public List<BookCopyReservation> findBookCopyReservationsByUser(User user) {
    if (user == null) {
      throw new NullPointerException("user is null");
    }
    return (List<BookCopyReservation>) em.createQuery(
            "SELECT b FROM BookCopyReservation b WHERE b.user = :user")
            .setParameter("user", user).getResultList();
  }

  @Override
  public List<BookCopyReservation> findBookCopyReservationsByReservationState(ReservationState rS) {
    if (rS == null) {
      throw new NullPointerException("reservation state is null");
    }
    return (List<BookCopyReservation>) em.createQuery(
            "SELECT b FROM BookCopyReservation b WHERE b.reservationState = :rS")
            .setParameter("rS", rS).getResultList();
  }

  @Override
  public List<BookCopyReservation> findAllBookCopyReservations() {
    return (List<BookCopyReservation>) em.createQuery(
            "SELECT b FROM BookCopyReservation b").getResultList();
  }

	@Override
	public List<BookCopyReservation> findBookCopyReservations(User user, ReservationState rS) {
		if (user == null) {
			throw new NullPointerException("user is null");
		}
		if (rS == null) {
			throw new NullPointerException("reservation state is null");
		}
		return em.createQuery(
				"SELECT b FROM BookCopyReservation b WHERE b.user = :user AND b.reservationState = :rS")
				.setParameter("user", user).setParameter("rS", rS)
				.getResultList();
	}
}
