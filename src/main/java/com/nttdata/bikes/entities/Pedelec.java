package com.nttdata.bikes.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class holds all the information for the Pedelec Table
 * For every Pedelec there is one entry in the table
 * The name of the table is "pedelec"
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Entity
@Table(name="Pedelec")
@XmlRootElement
public class Pedelec {
	
	/**
	 * A unique identifier for the pedelec.
	 * It is generated in an automated way.
	 * It's the key of the table.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	// The id of the pedelec is generated automatically
	@Column(name="PedelecId")
	private int pedelecId;	// This is the primary key of the table
	
	/**
	 * The name of the pedelec, which is needed for the employee to find the correct pedelec at the station.
	 */
	@Column(name="Name")
	private String name;
	
	/**
	 * The PIN of the pedelec, which is needed for the employee to unlock the pedelec at the station.
	 */
	@Column(name="Pin")
	private String pin;

	/**
	 * The current battery charging level of the pedelec.
	 * It is needed to calculate the maximum distance, for the pedelec at this moment.
	 */
	@Column(name="ChargingLevel")
	private double chargingLevel;
	
	/**
	 * The current charging status displays, whether the battery of the bike gets charged at the moment.
	 */
	@Column(name="ChargingStatus")
	private boolean chargingStatus;
	
	/**
	 * This boolean shows, whether the pedelec is functional or damaged at the moment.
	 * isFunctional == false -> damaged
	 */
	@Column(name="IsFunctional")
	private boolean isFunctional;
	
	/**
	 * This variable shows, whether the pedelec is reserved at the moment.
	 * It is not part of the table.
	 */
	@Transient
	private boolean isReserved;
	
	/**
	 * This variable holds the Id of the Employee who has reserved the Pedelec.
	 * It is not part of the table.
	 */
	
	@Transient
	private int reservedByEmployeeWithId;
	
	/**
	 * This is a foreign key 
	 * which holds the information of the Station in which the pedelec is parked
	 */
	@Transient
	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name = "PedelecStationId")
	private PedelecStation pedelecStation;	
	
	/**
	 * This private field holds only the id of the Station of the pedelec.
	 * We need it in order to send only the ID of the pedelecStation instead of 
	 * all the information of the Station with the JSON messages.
	 */
	private int station;
	
	public int getPedelecID() {
		return pedelecId;
	}

	public void setPedelecID(int pedelecID) {
		this.pedelecId = pedelecID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public double getChargingLevel() {
		return chargingLevel;
	}

	public void setChargingLevel(double chargingLevel) {
		this.chargingLevel = chargingLevel;
	}

	public boolean isChargingStatus() {
		return chargingStatus;
	}

	public void setChargingStatus(boolean chargingStatus) {
		this.chargingStatus = chargingStatus;
	}

	public boolean pedelecIsFunctional() {
		return isFunctional;
	}

	public void setIsFunctional(boolean isFunctional) {
		this.isFunctional = isFunctional;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public PedelecStation getPedelecStation_id() {
		return pedelecStation;
	}

	public void setPedelecStation_id(PedelecStation station_id) {
		this.pedelecStation = station_id;
		setStationId(station_id.getPedelecStationId());	// When the pedelec station changes we need to change also this variable
	}


	public int getStationId() {
		return station;
	}

	public void setStationId(int isInStation) {
		this.station = isInStation;
	}

	public int getReservedByEmployeeWithId() {
		return reservedByEmployeeWithId;
	}

	public void setReservedByEmployeeWithId(int reservedByEmployeeWithId) {
		this.reservedByEmployeeWithId = reservedByEmployeeWithId;
	}

	public Pedelec() {	}
	
	/**
	 * Constructor of the class Pedelec
	 */
	
	public Pedelec(String pedelecName, double chargingLevel,
			boolean chargingStatus, boolean pedelecIsFunctional,
			boolean isReserved, PedelecStation station) {
		this.name = pedelecName;
		this.chargingLevel = chargingLevel;
		this.chargingStatus = chargingStatus;
		this.isFunctional = pedelecIsFunctional;
		this.isReserved = isReserved;
		this.pedelecStation = station;
		this.station = station.getPedelecStationId();
	}

	public String toString() {
		return "Pedelec [idPedelec=" + pedelecId + ", pedelecName="
				+ name + ", chargingLevel=" + chargingLevel
				+ ", chargingStatus=" + chargingStatus
				+ ", pedelecIsFunctional=" + isFunctional + ", isReserved="
				+ isReserved + ", isInPedelecStation_id=" + station	+ "]";
	}
	
}
