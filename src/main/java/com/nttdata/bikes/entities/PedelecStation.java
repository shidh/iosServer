package com.nttdata.bikes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class holds the information for the PedelecStation table.
 * For every pedelec station there is one entry in the Table.
 * The name of the table is 'pedelecStation'.
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Entity
@Table(name="PedelecStation")
@XmlRootElement
public class PedelecStation {

	/**
	 * An unique identifier of the pedelec station.
	 * It is the primary key of the table.
	 */
	@Id
        @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PedelecStationId")
	private int pedelecStationId;	//This is the primary key of the table

	/**
	 * The name of the station.
	 * It is displayed in the application.
	 */
	@Column(name="Name")
	private String name;

	/**
	 * A station has limited space for pedelecs.
	 * This is the maximum count of pedelec, the station can have.
	 */
	@Column(name="MaxCapacity")
	private int  maxCapacity;

	/**
	 * The latitude of the GPS of the station.
	 */
	@Column(name="Latitude")
	private double latitude;

	/**
	 * The longitude of the GPS of the station.
	 */
	@Column(name="Longitude")
	private double longitude;

	public int getPedelecStationId() {
		return pedelecStationId;
	}

	public void setPedelecStationId(int pedelecStationId) {
		this.pedelecStationId = pedelecStationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxNumOfPedelecs() {
		return maxCapacity;
	}

	public void setMaxNumOfPedelecs(int maxNumOfPedelecs) {
		this.maxCapacity = maxNumOfPedelecs;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public PedelecStation() {	}

	/**
	 * Constructor for the PedelecStation
	 */

	public PedelecStation(String nameOfStation,
			int maxNumOfPedelecs, double latOfPedelecStation,
			double lonOfPedelecStation) {
		this.name = nameOfStation;
		this.maxCapacity = maxNumOfPedelecs;
		this.latitude = latOfPedelecStation;
		this.longitude = lonOfPedelecStation;
	}

	public String toString() {
		return "PedelecStation [idPedelecStation=" + pedelecStationId
				+ ", nameOfStation=" + name + ", maxNumOfPedelecs="
				+ maxCapacity + ", latOfPedelecStation="
				+ latitude + ", lonOfPedelecStation="
				+ longitude + "]";
	}

}
