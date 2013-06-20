package cz.muni.fi.jboss.book.web.util;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import javax.ejb.EJB;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: matejkobza
 * Date: 6/20/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Authenticator extends BaseAuthenticator implements org.jboss.seam.security.Authenticator {

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

    @Inject
    private Credentials credentials;

    private User user;

    @Override
    public void authenticate() {
        User tmpUser = accountManager.find(credentials.getUsername());
        if (tmpUser == null) {
            WebApplication.getReference().addErrorMessage("Login service", "Login failed. No such user.");
            setStatus(AuthenticationStatus.FAILURE);
            return;
        }
        if (!(credentials.getCredential() instanceof PasswordCredential)) {
            WebApplication.getReference().addErrorMessage("Login service", "Internal application error. Please contact administrator.");
            setStatus(AuthenticationStatus.FAILURE);
            return;
        }
        String pHash = PasswordUtils.hash(((PasswordCredential) credentials.getCredential()).getValue());
        if (tmpUser.getPassword().equals(pHash)) {
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(tmpUser);
        } else {
            WebApplication.getReference().addErrorMessage("Login service", "wrong password.<br />Form Hash:" + pHash + "<br />DB Hash:" + tmpUser.getPassword());
        }
    }

    @Override
    public void postAuthenticate() {
        // do log event
        //this.authenticated = true;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

}
