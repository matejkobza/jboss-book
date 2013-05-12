package cz.muni.fi.jboss.book.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class Book
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name = "Book")
public class Book implements Serializable {

  private static final long serialVersionUID = -3009934453727550437L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_Book", nullable = false)
  private Long id;
  @Column(name = "name")
  private String title;
  @Column(name = "publisher")
  private String publisher;
  @Column(name = "pages", nullable = false)
  private Integer pages;
  @Column(name = "ISBN", nullable = false)
  private Long ISBN;
  @OneToMany
  @JoinColumn(name = "ID_BookCopy")
  private List<BookCopy> bookCopies;
  
  @ManyToOne
  @JoinColumn(name = "ID_Author")
  private Author author;
  
  public List<BookCopy> getBookCopies() {
    return bookCopies;
  }

  public void setBookCopies(List<BookCopy> bookCopies) {
    this.bookCopies = bookCopies;
  }

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the publisher
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * @param publisher the publisher to set
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * @return the pages
   */
  public Integer getPages() {
    return pages;
  }

  /**
   * @param pages the pages to set
   */
  public void setPages(Integer pages) {
    this.pages = pages;
  }

  /**
   * @return the iSBN
   */
  public Long getISBN() {
    return ISBN;
  }

  /**
   * @param iSBN the iSBN to set
   */
  public void setISBN(Long iSBN) {
    ISBN = iSBN;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
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
    Book other = (Book) obj;
    if (ISBN == null) {
      if (other.ISBN != null) {
        return false;
      }
    } else if (!ISBN.equals(other.ISBN)) {
      return false;
    }
    return true;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Book [id=" + id + ", name=" + title + ", author=" + author
            + ", publisher=" + publisher + ", pages=" + pages + ", ISBN="
            + ISBN + "]";
  }
}
