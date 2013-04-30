package cz.muni.fi.jboss.book.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class Librarian
 *
 * @author Eduard Tomek
 *
 */
@Entity
public class Librarian extends User {

  private static final long serialVersionUID = -5004294651010492859L;
  @Column
  @Temporal(TemporalType.DATE)
  private Date hireDay;

  public Date getHireDay() {
    return hireDay;
  }

  public void setHireDay(Date hireDay) {
    this.hireDay = hireDay;
  }

  @Override
  public String toString() {
    return super.toString() + ", hiredSince=" + hireDay + "]";
  }
}
