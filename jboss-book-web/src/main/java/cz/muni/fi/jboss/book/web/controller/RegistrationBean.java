package cz.muni.fi.jboss.book.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import cz.muni.fi.jboss.book.ejb.security.AccountManager;
import cz.muni.fi.jboss.book.persistence.entity.User;

/**
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

    private User user;

    @EJB(name = "AccountManager")
    private AccountManager accountManager;

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
        user.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        user.setUsername(username);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String name) {
        this.fullname = name;
        user.setName(name);
    }

    public void register() {
        accountManager.register(user);
    }

    @PostConstruct
    public void initialize() {
        user = new User();
    }

    @Produces
    @Named
    public User getNewUser() {
        return user;
    }
}
