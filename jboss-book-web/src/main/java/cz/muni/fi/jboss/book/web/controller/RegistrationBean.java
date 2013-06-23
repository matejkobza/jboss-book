package cz.muni.fi.jboss.book.web.controller;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.core.WebBeanFactory;
import cz.muni.fi.jboss.book.web.util.PasswordUtils;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

    @Inject
    private Credentials credentials;

    private User user = new User();

    private String password1;

    private String password2;

    public void register() {
        if (!(this.user.getPassword().equals(getPassword2()))) {
            WebApplication.getReference().addErrorMessage("Registration", "passwords do not match.");
            return;
        }

        String hash = PasswordUtils.hash(getPassword2());
        this.user.setPassword(hash);
        user = accountManager.register(user);
        if (user != null) {
            WebApplication.getReference().addInfoMessage("Registration", "registration successful.");
            credentials.setUsername(this.user.getUsername());
            credentials.setCredential(new PasswordCredential(getPassword2()));
            WebBeanFactory.getIdentityBean().tryLogin();
        } else {
            WebApplication.getReference().addErrorMessage("Registration", "user registration was not successful.");
        }
    }

    public void createLibrarian() {
        if (!(this.user.getPassword().equals(getPassword2()))) {
            WebApplication.getReference().addErrorMessage("Registration", "passwords do not match.");
            return;
        }

        String hash = PasswordUtils.hash(getPassword2());
        this.user.setPassword(hash);
        user = accountManager.register(user);
        if (user != null) {
            user.setUserRole(UserRole.LIBRARIAN);
            accountManager.update(user);
            WebApplication.getReference().addInfoMessage("Registration", "librarian registration successful.");
        } else {
            WebApplication.getReference().addErrorMessage("Registration", "librarian registration was not successful.");
        }
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
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
