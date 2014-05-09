package com.nttdata.bikes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class holds all the information for the Transfering of the Reservations
 * For every Request there is one entry in the Table.
 * The name of the table is "transferrequest".
 * @author Stefanos Stamogiorgos
 */
@Entity
@Table(name = "TransferRequest")
@XmlRootElement
public class TransferRequest {

	/**
	 * A unique transferRequestId for each TransferRequest
 It is not used somewhere we just need it as a primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TransferRequestId")
	private int transferRequestId;
	
	/**
	 * The Id of the Employee who is requesting another to get his reservation
	 */

	@Column(name = "RequesterId")
	private int requesterId;
	
	/**
	 * The Id of the Employee who has a reservation already
	 */

	@Column(name = "HolderId")
	private int holderId;
	
	/**
	 * The Id of the Reservation which is requested.
	 */

	@Column(name = "ReservationId")
	private int reservationId;
        
	public int getTransferRequestId() {
		return transferRequestId;
	}

	public int getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(int requesterId) {
		this.requesterId = requesterId;
	}

	public int getHolderId() {
		return holderId;
	}

	public void setHolderId(int holderId) {
		this.holderId = holderId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	
	public TransferRequest() {}

	public TransferRequest(int requesterId, int holderId, int reservationId) {
		this.requesterId = requesterId;
		this.holderId = holderId;
		this.reservationId = reservationId;
	}

	@Override
	public String toString() {
		return "TransferRequest [id=" + transferRequestId + ", requesterId=" + requesterId
				+ ", holderId=" + holderId + ", reservationId=" + reservationId
				+ "]";
	}

}
