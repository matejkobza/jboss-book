package cz.muni.fi.jboss.book.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;

/**
 * 
 * @author matejkobza
 */
@ManagedBean
@SessionScoped
@Deprecated
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1516686394858785542L;

	@EJB(name = "AccountManager")
	private AccountManager accountManager;

	private boolean authenticated = false;

	private String username;
	private String password;

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

	public boolean isAuthenticated() {
		return this.authenticated;
	}

    //@TODO implement
	public void login() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		//if (accountManager.login(user)) {
			this.authenticated = true;
		//}
	}

    //@TODO implement
	public void logout() {
        //User user = this.accountManager.find(username);
        //this.accountManager.logout(user);
		this.authenticated = false;
	}

    //@TODO
    public boolean isLibrarian() {
        this.accountManager.find(username);
        return true;
    }

}
