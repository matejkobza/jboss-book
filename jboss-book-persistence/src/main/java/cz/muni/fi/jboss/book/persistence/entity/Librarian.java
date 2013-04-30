/**
 * Librarian.java
 *
 * @author Eduard Tomek
 */
package cz.muni.fi.library.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
