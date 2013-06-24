package cz.muni.fi.jboss.book.persistence.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import cz.muni.fi.jboss.book.persistence.ReservationState;

/**
 * @author Eduard Tomek
 */
@Entity
@Table(name = "BOOKCOPYRESERVATION")
public class BookCopyReservation implements Serializable {

    private static final long serialVersionUID = -83788868169501821L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_BOOKCOPYRESERVATION")
    private Long id;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @NotNull
    private User user;


    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    @NotNull
    private ReservationState reservationState;

    @ManyToOne(targetEntity = BookCopy.class, fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ID_BOOKCOPY", referencedColumnName = "ID_BOOKCOPY")
    @NotNull
    private BookCopy bookCopy;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    // commented out because of a good reason :-)
    // current date is set in ReservationManagerImpl.reserveBook() (not past)
    //@Past//purchase date must the date in the past
    @NotNull
    private Date created;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date createDate) {
        this.created = createDate;
    }

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
        return "BookCopyReservation{" + "id=" + id + ", bookCopy=" + bookCopy + ", user=" + user
                + ", reservationState=" + reservationState + '}';
    }
}
