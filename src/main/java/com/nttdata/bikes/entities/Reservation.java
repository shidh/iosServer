package com.nttdata.bikes.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nttdata.bikes.database.EntityManagerFactorySingleton;

/*
 * This is the class which holds the information for the reservation Table
 * The name of the table is 'reservation'
 */

/**
 * This class holds all the information for the Reservation Table.
 * For every Reservation there is one entry in the Table.
 * The name of the table is "reservation".
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Entity
@Table(name="Reservation")
@XmlRootElement

public class Reservation {
	
	/**
	 * A unique id for each reservation.
	 * It is generated in an automated way.
	 * It is they key of the table.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ReservationId")
	private int reservationId;
	
	/**
	 * A foreign key which represents the Pedelec of the Reservation.
	 */
	@Transient
	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name="PedelecId", referencedColumnName="PedelecId")
	private Pedelec pedelec;	
	
	/**
	 * A foreign key which represents the Employee who did the reservation .
	 */
	@Transient
	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name="EmployeeId", referencedColumnName="EmployeeId")
	private Employee employee;	
	
	/**
	 * The time that the reservation starts.
	 */
	
	@Column(name="StartDateTime")
	private long startDateTime;
	
	/**
	 * The time that the reservation ends.
	 */
	
	@Column(name="EndDateTime")
	private long endDateTime;
	
	/**
	 * The total number of KMs crossed.
	 */
	
	@Column(name="TotalKm")
	private double totalKm;
	
	/**
	 * The total travel time.
	 */
	
	@Column(name="TotalTravelTime")
	private double totalTravelTime;
	
	/**
	 * The average Speed.
	 */
	
	@Column(name="AverageSpeed")
	private double averageSpeed;
	
	/**
	 * Maximum Speed reached.
	 */
	
	@Column(name="MaxSpeed")
	private double maxSpeed;
	
	/**
	 * The id of the pedelec of the reservation.
	 * It is not stored in the database.
	 */

	private int pedelecId;
	
	/**
	 * The id of the employee who did the reservation.
	 * It is not stored in the database.
	 */
	
	private int employeeId;
	
	
	public int getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(int idReservation) {
		this.reservationId = idReservation;
	}
	
	public Pedelec getPedelec() {
		return pedelec;
	}

	public void setPedelec(Pedelec pedelec) {
		this.pedelec = pedelec;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public long getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(long startDateTime) {
		this.startDateTime = startDateTime;
	}

	public long getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(long endDateTime) {
		this.endDateTime = endDateTime;
	}

	public double getTotalKm() {
		return totalKm;
	}

	public void setTotalKm(double totalKm) {
		this.totalKm = totalKm;
	}

	public double getTotalTravelTime() {
		return totalTravelTime;
	}

	public void setTotalTravelTime(double totalTravelTime) {
		this.totalTravelTime = totalTravelTime;
	}

	public double getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Reservation() {	}
	
	/**
	 * Constructor of class Employee
	 * This constructor is used when we pre-fill the database with some data.
	 */

	public Reservation(Pedelec pedelec, Employee employee,
			long startDateTime, long endDateTime,
			double totalKm, double totalTravelTime, double averageSpeed,
			double maxSpeed) {
		this.pedelec = pedelec;
		this.employee = employee;
		this.pedelecId = pedelec.getPedelecID();
		this.employeeId = employee.getEmployeeId();
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.totalKm = totalKm;
		this.totalTravelTime = totalTravelTime;
		this.averageSpeed = averageSpeed;
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * Constructor of the class Reservation
	 * This constructor is used in the reservation resource, when a new reservation is created and shall be connected to a pedelec and
	 * an employee in the database.
	 */
	
	public Reservation(int pedelecId, int employeeId,
			long startDateTime, long endDateTime,
			double totalKm, double totalTravelTime, double averageSpeed,
			double maxSpeed) {
		this.pedelecId = pedelecId;
		this.employeeId = employeeId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.totalKm = totalKm;
		this.totalTravelTime = totalTravelTime;
		this.averageSpeed = averageSpeed;
		this.maxSpeed = maxSpeed;
		
		EntityManagerFactory factory =  EntityManagerFactorySingleton.getSharedInstance();
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("select p from Pedelec p WHERE p.pedelecId = '" + pedelecId + "'");
		@SuppressWarnings("unchecked")
		List<Pedelec> pedelec = q.getResultList();
		this.pedelec = pedelec.get(0);
		
		q = em.createQuery("select e from Employee e WHERE e.employeeId = '" + employeeId + "'");
		@SuppressWarnings("unchecked")
		List<Employee> employee = q.getResultList();
		this.employee = employee.get(0);
	}
	

	public int getPedelec_id() {
		return pedelecId;
	}


	public void setPedelec_id(int pedelec_id) {
		this.pedelecId = pedelec_id;
	}

	public int getEmployee_id() {
		return employeeId;
	}

	public void setEmployee_id(int employee_id) {
		this.employeeId = employee_id;	// update the class variable EmployeeId
		EntityManagerFactory factory =  EntityManagerFactorySingleton.getSharedInstance();
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("select e from Employee e WHERE e.employeeId = '" + employee_id + "'");	// search the db for the employee with the specified id
		@SuppressWarnings("unchecked")
		List<Employee> employee = q.getResultList();
		setEmployee(employee.get(0));	// update the class variable employee 
	
	}
	
	public void setOnlyEmployee_id(int employee_id) {
		this.employeeId = employee_id;	// update the class variable EmployeeId
	}

	@Override
	public String toString() {
		return "Reservation [idReservation=" + reservationId + ", pedelec="
				+ pedelec + ", employee=" + employee + ", startDateTime="
				+ startDateTime + ", endDateTime=" + endDateTime + ", totalKm="
				+ totalKm + ", totalTravelTime=" + totalTravelTime
				+ ", averageSpeed=" + averageSpeed + ", maxSpeed=" + maxSpeed
				+ ", pedelec_id=" + pedelecId + ", employee_id=" + employeeId
				+ "]";
	}

}
