package cz.muni.fi.jboss.book.web.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.inject.Alternative;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.UserRole;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.util.Authenticator;
import cz.muni.fi.jboss.book.web.util.PasswordUtils;
import org.jboss.seam.security.IdentityImpl;


/**
 * @author matejkobza
 */
@ManagedBean
@SessionScoped
@Alternative
public class LoginBean extends IdentityImpl implements Serializable {

    private static final long serialVersionUID = 1516686394858785542L;

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

    private User user;

    public User getUser() {
        if(this.user == null) {
            this.user = (User)super.getUser();
        }
        return this.user;
    }

    public boolean isManager() {
        if (this.user != null) {
            return this.user.getUserRole().equals(UserRole.MANAGER);
        }
        return false;
    }

    public boolean isLibrarian() {
        if (this.user != null) {
            return this.user.getUserRole().equals(UserRole.LIBRARIAN);
        }
        return false;
    }

    public boolean isReader() {
        if (this.user != null) {
            return this.user.getUserRole().equals(UserRole.READER);
        }
        return false;
    }

}
