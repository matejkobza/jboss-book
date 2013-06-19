package cz.muni.fi.jboss.book.web.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.core.WebBeanFactory;

/**
 * 
 * @author matejkobza
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1516686394858785542L;

	@EJB(name = "AccountManager")
	private AccountManager accountManager;

	private boolean authenticated = false;
    private User user = new User();
    private String password2;

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    //@TODO implement there should be performed check
	public boolean isAuthenticated() {
		return this.authenticated;
	}

    //@TODO implement
	public void login() {
	    this.authenticated = true;
	}

    //@TODO implement
	public void logout() {
        this.authenticated = false;
	}

    //@TODO implement
    public boolean isLibrarian() {
        return true;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void register() {
        if(!(this.user.getPassword().equals(getPassword2()))) {
            WebApplication.getReference().addErrorMessage("Registration", "password does not match.");
            return;
        }
        user = accountManager.register(user);
        if(user != null) {
            WebApplication.getReference().addInfoMessage("Registration", "you have been registered and logged in.");
            WebBeanFactory.getLoginBean().setUser(user);
        } else {
            WebApplication.getReference().addErrorMessage("Registration", "user registration not successful.");
        }
    }

}
