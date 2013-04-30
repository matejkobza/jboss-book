/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.library.entity;

import cz.muni.fi.library.enums.ReservationState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name="BookCopyReservation")
public class BookCopyReservation {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="ID_BookCopy")
    @Column(nullable=false)
    private BookCopy bookCopy;
    
    @ManyToOne
    @JoinColumn(name="ID_User")
    @Column(nullable=false)
    private User user;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable=false)
    private ReservationState reservationState;

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

    public ReservationState getReservationState() {
        return reservationState;
    }

    public void setReservationState(ReservationState reservationState) {
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
        return "BookCopyReservation{" + "id=" + id + ", bookCopy=" + bookCopy + ", user=" + user + ", reservationState=" + reservationState + '}';
    }
    
}
