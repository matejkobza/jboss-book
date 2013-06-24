package cz.muni.fi.jboss.book.web.controller;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;
import cz.muni.fi.jboss.book.web.core.WebApplication;
import cz.muni.fi.jboss.book.web.core.WebBeanFactory;
import cz.muni.fi.jboss.book.web.util.PasswordUtils;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: matejkobza
 * Date: 6/21/13
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class SettingsBean implements Serializable {

    private IdentityBean identityBean;

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

    private String password1;
    private String password2;
    private String newPassword;
    private String username;

    public SettingsBean() {
        this.identityBean = WebBeanFactory.getIdentityBean();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void updateName() {
        User u = identityBean.getUser();
        u.setName(this.username);
        accountManager.update(u);
        identityBean.setUser(u);
        WebApplication.getReference().addInfoMessage("Account settings", "user updated successfully.");
    }

    public void updatePassword() {
        if (this.password1.equals(this.password2)) {
            if (PasswordUtils.hash(this.password1).equals(identityBean.getUser().getPassword())) {
                User u = identityBean.getUser();
                u.setPassword(PasswordUtils.hash(newPassword));
                accountManager.update(u);
                identityBean.setUser(u);
                WebApplication.getReference().addInfoMessage("Account settings", "password changed successfully.");
            } else {
                WebApplication.getReference().addErrorMessage("Account settings", "wrong password entered.");
            }
        } else {
            WebApplication.getReference().addErrorMessage("Account settings", "passwords do not match.");
        }

    }
}
