package cz.muni.fi.jboss.book.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import cz.muni.fi.jboss.book.ejb.manager.ReservationManager;
import cz.muni.fi.jboss.book.persistence.ReservationState;
import cz.muni.fi.jboss.book.persistence.entity.BookCopyReservation;

@ManagedBean
@ViewScoped
public class ReservationsBean {

	@EJB(name = "ReservationManager")
	private ReservationManager reservationManager;

	private Long reservationState;
	private Map<String, Long> reservationStates = new HashMap<String, Long>();
	private List<BookCopyReservation> reservations = null;

	public ReservationsBean() {
		//reservationStates.put("all", 0L);
		reservationStates.put("new", 1L);
		reservationStates.put("ready", 2L);
		reservationStates.put("lent", 3L);
		reservationStates.put("returned", 4L);
	}
	
	private ReservationState numberToReservationState(Long stateNum) {
		if (stateNum.equals(1L))
			return ReservationState.NEW;
		else if (stateNum.equals(2L))
			return ReservationState.READY;
		else if (stateNum.equals(3L))
			return ReservationState.LENT;
		else if (stateNum.equals(4L))
			return ReservationState.RETURNED;
		else
			return null;
	}

	public List<BookCopyReservation> getReservations() {
		if (reservations == null)
			reservations = reservationManager.getAllBookCopyReservations(false);
		return reservations;
	}

	public Long getReservationState() {
		return reservationState;
	}

	public void setReservationState(Long reservationState) {
		this.reservationState = reservationState;
	}

	public Map<String, Long> getReservationStates() {
		return reservationStates;
	}
	
	public void handleReservationStateChange() {
		ReservationState state = numberToReservationState(this.reservationState);
		if (state == null) {
			reservations = null;
		} else {
			reservations = reservationManager.getBookCopyReservations(null, state);
		}
	}

}
