package cz.muni.fi.jboss.book.web;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author matejkobza
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
  
  private static final long serialVersionUID = 1516686394858785542L;
  private boolean authenticated = false;
  
  public boolean isAuthenticated() {
    return this.authenticated;
  }
  
  // @TODO implement login
  public void login() {
    this.authenticated = true;
  }
  
  //@TODO implement logout
  public void logout() {
    this.authenticated = false;
  }
  
}
