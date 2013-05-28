package cz.muni.fi.jboss.book.web;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author matejkobza
 */
@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {

  private static final long serialVersionUID = -2909783378167581433L;
  private String fullname;
  private String username;
  private String password;
  private String password2;

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String name) {
    this.fullname = name;
  }

  public void register() {
    //@TODO implementation
  }
}
