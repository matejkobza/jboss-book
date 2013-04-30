/**
 * Manager.java
 *
 * @author Eduard Tomek
 */
package cz.muni.fi.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Class Manager
 *
 * @author Eduard Tomek
 *
 */
@Entity
public class Manager extends User{
	 
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
