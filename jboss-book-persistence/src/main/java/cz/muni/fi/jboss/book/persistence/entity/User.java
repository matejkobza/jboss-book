package cz.muni.fi.jboss.book.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Class User
 *
 * @author Eduard Tomek
 */
@Entity
@Table(name = "UserTable")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements Serializable, org.picketlink.idm.api.User{

  private static final long serialVersionUID = -5392116112798403077L;
  @Id
  @Column(name = "username", nullable = false)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "name")
  private String name;

  public String getId() {
    return username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((username == null) ? 0 : username.hashCode());
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
	User other = (User) obj;
	if (username == null) {
		if (other.username != null)
			return false;
	} else if (!username.equals(other.username))
		return false;
	return true;
}

@Override
public String getKey() {
	return username+password;
}
}
