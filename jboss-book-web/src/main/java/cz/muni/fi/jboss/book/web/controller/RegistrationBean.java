package cz.muni.fi.jboss.book.web.controller;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.util.PasswordUtils;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: matejkobza
 * Date: 6/20/13
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

    private User user = new User();

    private String password2;

    public void register() {
        if (!(this.user.getPassword().equals(getPassword2()))) {
            WebApplication.getReference().addErrorMessage("Registration", "password does not match.");
            return;
        }

        String hash = PasswordUtils.hash(getPassword2());
        this.user.setPassword(hash);
        user = accountManager.register(user);
        if (user != null) {
            WebApplication.getReference().addInfoMessage("Registration", "registration successful.");
        } else {
            WebApplication.getReference().addErrorMessage("Registration", "user registration not successful.");
        }
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
