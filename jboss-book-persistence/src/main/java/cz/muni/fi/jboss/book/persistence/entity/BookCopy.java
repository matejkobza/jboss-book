package cz.muni.fi.jboss.book.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;

import org.hibernate.annotations.IndexColumn;

/**
 * Class BookCopy
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name = "BookCopy")
public class BookCopy implements Serializable {

  private static final long serialVersionUID = 4881074127449326542L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_BookCopy", nullable = false)
  private Long id;
  @Column(nullable = false)
  @Temporal(javax.persistence.TemporalType.DATE)
  @Past//purchase date must the date in the past
  private Date purchaseDate;
  
  @ManyToOne(targetEntity=Book.class, fetch= FetchType.EAGER, cascade= CascadeType.REFRESH)
  @JoinColumn(name = "ID_Book", referencedColumnName = "ID_Book")
  private Book book;

  @OneToMany(targetEntity=BookCopyReservation.class, fetch= FetchType.EAGER,
		  cascade=CascadeType.REFRESH)
  @IndexColumn(name = "ID_BookCopyReservation")
  private List<BookCopyReservation> bookCopyReservations;
  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;

  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  /**
   * @return the book
   */
  public Book getBook() {
    return book;
  }

  /**
   * @param book the book to set
   */
  public void setBook(Book book) {
    this.book = book;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BookCopy other = (BookCopy) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "BookCopy [id=" + id + ", book=" + book + "]";
  }
}
