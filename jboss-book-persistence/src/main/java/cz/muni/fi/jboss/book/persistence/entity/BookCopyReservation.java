package cz.muni.fi.jboss.book.persistence.entity;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cz.muni.fi.jboss.book.persistence.ReservationStateEnum;

/**
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name = "BookCopyReservation")
public class BookCopyReservation implements Serializable {

  private static final long serialVersionUID = -83788868169501821L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_BookCopyReservation")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ID_User")
  private User user;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private ReservationStateEnum reservationState;
  
  @OneToOne(targetEntity=BookCopy.class, fetch= FetchType.EAGER,
		  cascade=CascadeType.REFRESH, optional = false)
  @JoinColumn(name = "ID_BookCopy", referencedColumnName = "ID_BookCopy")
  private BookCopy bookCopy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BookCopy getBookCopy() {
    return bookCopy;
  }

  public void setBookCopy(BookCopy bookCopy) {
    this.bookCopy = bookCopy;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ReservationStateEnum getReservationState() {
    return reservationState;
  }

  public void setReservationState(ReservationStateEnum reservationState) {
    this.reservationState = reservationState;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BookCopyReservation other = (BookCopyReservation) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "BookCopyReservation{" + "id=" + id + ", bookCopy=" + bookCopy + ", user="// + user 
    		+ ", reservationState=" + reservationState + '}';
  }
}
