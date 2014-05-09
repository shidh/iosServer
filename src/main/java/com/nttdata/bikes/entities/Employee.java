package com.nttdata.bikes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This class holds all the information for the Employee Table.
 * For every Employee there is one entry in the Table.
 * The name of the table is "employee".
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Entity
@Table(name="Employee")
@XmlRootElement
public class Employee {
	

	/**
	 * A unique id for employee.
	 * It's the key of the table.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EmployeeId")
	private int employeeId;	// Primary key column
	
	/**
	 * The name of the employee.
	 */
	@Column(name="Name")
	private String name;
	
	/**
	 * The surname of the employee.
	 */
	@Column(name="Surname")
	private String surname;
	
	/**
	 * The username of the employee, which he needs for using the application.
	 */
	@Column(name="Username")
	private String username;
	
	/**
	 * The password of the employee, which he needs for using the application.
	 */
	@Column(name="Password")
	private String password;
	
	/**
	 * The mobile number of the cell phone of the employee
	 */
	@Column(name="MobileNo")
	private String mobileNo;
	
	/**
	 * The total travel time of the employee with any pedelec.
	 * It is used for statistics.
	 */
	@Column(name="TotalTravelTime")
	private double totalTravelTime;
	
	/**
	 * The total travel kilometers of the employee with any pedelec.
	 * It is used for statistics.
	 */
	@Column(name="TotalKm")
	private double totalKm;

	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int idPedelec) {
		this.employeeId = idPedelec;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public double getTotalTravelTime() {
		return totalTravelTime;
	}

	public void setTotalTravelTime(double totalTravelTime) {
		this.totalTravelTime = totalTravelTime;
	}

	public double getTotalKm() {
		return totalKm;
	}

	public void setTotalKm(double totalKm) {
		this.totalKm = totalKm;
	}
	
	public Employee() {	}

	/**
	 * Constructor of the class Employee
	 */
	
	public Employee(String name, String surname,
			String username, String password, String mobileNo, double travelTime, double totalKm) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.mobileNo = mobileNo;
		this.totalTravelTime = travelTime;
		this.totalKm = totalKm;
	}

	public String toString() {
		return "Employee [idEmployee=" + employeeId + ", name=" + name
				+ ", surname=" + surname + ", username=" + username
				+ ", password=" + password + ", mobileNo=" + mobileNo + ", travelTime="
				+ totalTravelTime + ", totalKm=" + totalKm + "]";
	}
	

}
