package cz.muni.fi.jboss.book.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Class Reader
 *
 * @author Eduard Tomek
 *
 */
@Entity
public class Reader extends User {

  private static final long serialVersionUID = -422999082678393615L;
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
