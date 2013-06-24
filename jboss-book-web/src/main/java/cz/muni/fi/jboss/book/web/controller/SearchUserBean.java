package cz.muni.fi.jboss.book.web.controller;

import cz.muni.fi.jboss.book.ejb.manager.UserManager;
import cz.muni.fi.jboss.book.persistence.entity.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SearchUserBean implements Serializable {

    @EJB(name = "UserManager")
    private UserManager userManager;

    private String username;
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        if(this.users.isEmpty()) {
            this.users.addAll(userManager.getReaders());
            this.users.addAll(userManager.getLibrarians());
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void searchByFilter() {
        this.users.clear();
        this.users.addAll(userManager.searchUsers(this.username));
    }

}
