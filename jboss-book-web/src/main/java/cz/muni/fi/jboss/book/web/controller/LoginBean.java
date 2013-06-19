package cz.muni.fi.jboss.book.web.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

/**
 * 
 * @author matejkobza
 */
@ManagedBean
@SessionScoped
public class LoginBean extends BaseAuthenticator implements Serializable, Authenticator {

	private static final long serialVersionUID = 1516686394858785542L;

	@EJB(name = "AccountManager")
	private AccountManager accountManager;

    @Inject
    private Credentials credentials;
    @Inject
    private Identity identity;

	private boolean authenticated = false;
    private User user = new User();
    private org.picketlink.idm.api.User seamUser;
    private AuthenticationStatus authenticationStatus;



    @Override
    public void authenticate() {
        if ("demo".equals(credentials.getUsername()) &&

                credentials.getCredential() instanceof PasswordCredential &&

                "demo".equals(((PasswordCredential) credentials.getCredential()).getValue()))  {

            setStatus(AuthenticationStatus.SUCCESS);

            setUser(new SimpleUser("demo"));

        }
    }

    @Override
    public void postAuthenticate() {
        //do some additional staff when authenticated
        this.authenticated = true;
    }

    @Override
    public org.picketlink.idm.api.User getUser() {
        return this.seamUser;
    }

    @Override
    public AuthenticationStatus getStatus() {
        return this.authenticationStatus;
    }

    public void setAuthenticationStatus(AuthenticationStatus status) {
        this.authenticationStatus = status;
    }

    public void setUser(org.picketlink.idm.api.User user) {
        this.seamUser = user;
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    /**
     * Checks if user has some role.
     * @param role
     * @param group
     * @param groupType
     * @return
     */
    public boolean hasRole(String role, String group, String groupType) {
        return identity.hasRole(role, group, groupType);
    }

    public boolean inGroup(String name, String groupType) {
        return identity.inGroup(name, groupType);
    }

    /* registration */

    private String password2;

    public void register() {
        if(!(this.user.getPassword().equals(getPassword2()))) {
            WebApplication.getReference().addErrorMessage("Registration", "password does not match.");
            return;
        }
        user = accountManager.register(user);
        if(user != null) {
            WebApplication.getReference().addInfoMessage("Registration", "registration successful.");
            //WebBeanFactory.getLoginBean().setUser(user);
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
}
