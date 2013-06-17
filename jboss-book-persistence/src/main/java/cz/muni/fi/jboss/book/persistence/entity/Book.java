package cz.muni.fi.jboss.book.persistence.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.IndexColumn;

/**
 * Class Book
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name = "BOOK")
public class Book implements Serializable {

  private static final long serialVersionUID = -3009934453727550437L;
  private static final int TITLE_LENGTH = 100;
  private static final int PUBLISHER_LENGTH = 100;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_BOOK", nullable = false)
  private Long id;
  @Column(name = "name", length = TITLE_LENGTH)
  @Size(max = TITLE_LENGTH)
  private String title;
  @Column(name = "PUBLISHER", length = PUBLISHER_LENGTH)
  @Size(max = PUBLISHER_LENGTH)
  private String publisher;
  @Column(name = "PAGES", nullable = false)
  @Min(0)
  private Integer pages;
  @Column(name = "ISBN", nullable = false)
  //ISBN from the year 2007 has 13 numbers
  //older ISBN standarts has less than  13 numbers, so 13 is maximum
  @Digits(integer = 13, fraction = 0) 
  private Long ISBN;
  
  @OneToMany(targetEntity=BookCopy.class, fetch= FetchType.EAGER, cascade= CascadeType.REFRESH)
  @IndexColumn(name="BOOKCOPY_ID")
  private List<BookCopy> bookCopies;
  
  @ManyToOne(targetEntity=Author.class, fetch= FetchType.EAGER, cascade= CascadeType.REFRESH)
  @JoinColumn(name = "ID_AUTHOR", referencedColumnName = "ID_AUTHOR")
  private Author author;

  
  
  public List<BookCopy> getBookCopies() {
    return bookCopies;
  }

  public void setBookCopies(List<BookCopy> bookCopies) {
    this.bookCopies = bookCopies;
  }
  
public Author getAuthor() {
	return author;
}

public void setAuthor(Author author) {
	this.author = author;
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
  public String toString() {
    return "Book [id=" + id + ", name=" + title + ", author=" + author
            + ", publisher=" + publisher + ", pages=" + pages + ", ISBN="
            + ISBN + "]";
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Book other = (Book) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}
}
