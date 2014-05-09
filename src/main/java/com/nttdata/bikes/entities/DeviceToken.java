package com.nttdata.bikes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class holds the information for all the device tokens necessary for the Apple Push Notification Service.
 * For every employee we can have multiple entries in the table because he might use more than one Devices.
 * The name of the table is "devicetoken"
 * @author Stefanos Stamogiorgos
 */

@Entity
@Table(name="DeviceToken")
@XmlRootElement

public class DeviceToken {
	
	/**
	 * A unique Id for each entry , it is not used somewhere we just need it as a primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DeviceTokenId")
	private int deviceTokenId;
	
	/**
	 * The deviceTokenId of the respective Employee
	 */
	@Column(name= "EmployeeId")
	private int employeeId;
	
	/**
	 * One Token for each Device
	 */
	@Column(name = "TokenString")
	private String tokenString;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getTokenString() {
		return tokenString;
	}

	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}

	public int getDeviceTokenId() {
		return deviceTokenId;
	}
	
	public DeviceToken() { }
	
	/**
	 * Constructor of the class DeviceToken
	 */

	public DeviceToken(int employeeId, String deviceToken) {
		this.employeeId = employeeId;
		this.tokenString = deviceToken;
	}

	@Override
	public String toString() {
		return "DeviceToken [id=" + deviceTokenId + ", employeeId=" + employeeId + ", "
				+ (tokenString != null ? "deviceToken=" + tokenString : "")
				+ "]";
	}
	
	

}
