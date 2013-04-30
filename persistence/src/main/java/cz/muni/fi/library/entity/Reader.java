/**
 * Class Reader.java
 *
 * @author Eduard Tomek
 */
package cz.muni.fi.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Class Reader
 *
 * @author Eduard Tomek
 *
 */
@Entity
public class Reader extends User{
	
	@Column
	private String aboutMe;

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	@Override
	public String toString() {
		return super.toString() + ", aboutMe=" + aboutMe + "]";
	}

		
}
