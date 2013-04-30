package cz.muni.fi.jboss.book.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Class Manager
 *
 * @author Eduard Tomek
 *
 */
@Entity
public class Manager extends User {

  private static final long serialVersionUID = 969250111320136365L;
  @Column
  private int salary;

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  @Override
  public String toString() {
    return super.toString() + ", salary=" + salary + "]";
  }
}
