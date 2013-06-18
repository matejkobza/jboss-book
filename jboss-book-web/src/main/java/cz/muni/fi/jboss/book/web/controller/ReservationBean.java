package cz.muni.fi.jboss.book.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;
import cz.muni.fi.jboss.book.web.core.WebBeanFactory;

@ManagedBean
@ViewScoped
public class ReservationBean {

	@EJB(name = "ReservationManager")
	private ReservationManager reservationManager;

	private String readerUsername;
	private Long bookCopyId;

	public String getReaderUsername() {
		return readerUsername;
	}

	public void setReaderUsername(String readerUsername) {
		this.readerUsername = readerUsername;
	}

	public Long getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(Long bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	public boolean reserve(ActionEvent event) {
		//String readerUsername = (String) event.getComponent().getAttributes().get("readerUsername");
        String username = WebBeanFactory.getLoginBean().getUsername();
        if(username.isEmpty()) {
            return false;
        } else {
		    Long bookCopyId = (Long) event.getComponent().getAttributes().get("bookCopyId");
		    reservationManager.reserveBook(bookCopyId, username);
		    // TODO
		    return true;
        }
	}
}
