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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.IndexColumn;

/**
 * Class Author
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name = "Author")
public class Author implements Serializable {

  private static final long serialVersionUID = -300993445327550437L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_Author", nullable = false)
  private Long id;
  
  @Column(name = "firstName")
  @Size(max = 20)
  private String firstName;
  
  @Column(name = "surname")
  @Size(max = 40)
  private String surname;
  
  @Column(name = "description")
  private String description;
  
  
  @OneToMany(targetEntity=Book.class, fetch= FetchType.EAGER, cascade= CascadeType.REFRESH)
  @IndexColumn(name = "Book_ID")
  private List<Book> books;
  
  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getFirstName() {
	return firstName;
  }

  public void setFirstName(String firstName) {
	this.firstName = firstName;
  }

  public String getSurname() {
	return surname;
  }

  public void setSurname(String surname) {
	this.surname = surname;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public List<Book> getBooks() {
	return books;
  }

  public void setBooks(List<Book> books) {
	this.books = books;
  }

  public static long getSerialversionuid() {
	return serialVersionUID;
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
	Author other = (Author) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}

	@Override
	public String toString() {
		return "Author [id=" + id + ", firstName=" + firstName + ", surname="
				+ surname + ", description=" + description + ", books=" //+ books
				+ "]";
	}

  
  

}