package cz.muni.fi.jboss.book.persistence.dao;

import java.util.List;

import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.entity.BookCopy;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;
import cz.muni.fi.jboss.book.persistence.entity.User;

/**
 *
 * @author Eduard Tomek
 */
public interface BookCopyReservationDAO {

  /**
   * Create Book Copy reservation
   *
   * @param bookCopyReservation
   * @return created book copy reservation
   * @throws NullPointerException if bookCopyReservation is null
   * @throws IllegalArgumentException if User, BookCopy or ReservationState is
   * null
   */
  BookCopyReservation createBookCopyReservation(BookCopyReservation bookCopyReservation);

  /**
   * Update Book copy reservation. Updates only reservation state!
   *
   * @param bookCopyReservation
   * @return updated book copy reservation
   * @throws NullPointerException if bookCopyReservation is null
   * @throws IllegalArgumentException if Id, User, BookCopy or ReservationState is
   * null
   */
  BookCopyReservation updateBookCopyReservation(BookCopyReservation bookCopyReservation);

  /**
   * Delete book copy reservation
   *
   * @param bookCopyReservation
   * @throws IllegalArgumentException if Id is null
   */
  void deleteBookCopyReservation(BookCopyReservation bookCopyReservation);

  /**
   * Find book copy reservation by Id
   *
   * @param id
   * @return book copy reservation with given Id, if there is no such
   * reservation then return null
   * @throws NullPointerException if id is null
   */
  BookCopyReservation findBookCopyReservationById(Long id);

  /**
   * Find book copy reservations by book copy
   *
   * @param bookCopy
   * @return all reservations on bookCopy (all reservation states), if there is
   * no such reservation then empty list is returned
   */
  List<BookCopyReservation> findBookCopyReservationsByBookCopy(BookCopy bookCopy);

  /**
   * Find book copy reservations by user
   *
   * @param user
   * @return all reservations of user (all reservation states), if there is no
   * such reservation then empty list is returned
   */
  List<BookCopyReservation> findBookCopyReservationsByUser(User user);

  /**
   * Find book copy reservations by reservation state
   *
   * @param rS enumeration reservation state
   * @return all reservation with given reservation state
   */
  List<BookCopyReservation> findBookCopyReservationsByReservationState(ReservationState rS);
  
  List<BookCopyReservation> findBookCopyReservations(User user, ReservationState rS);

  /**
   * Find all book copy reservations
   *
   * @return all book copy reservations
   */
  List<BookCopyReservation> findAllBookCopyReservations();
}
