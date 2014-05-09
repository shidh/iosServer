package com.nttdata.bikes.entities;

import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class holds all the information for the DamageReport Table. For every
 * damage report there is one entry in the Table. The name of the table is
 * "damage_report".
 *
 * @author Gerhard Henning
 */

@Entity
@Table(name = "DamageReport")
@XmlRootElement
public class DamageReport {

	/**
	 * A unique id for each damage report. It is generated in an automated way.
	 * It is they key of the table.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DamageReportId")
	private int damageReportId;

	/**
	 * A foreign key which represents the Pedelec for that the damage has ben
	 * reported.
	 */
	@Transient
	@ManyToOne
	@JoinColumn(name = "PedelecId", referencedColumnName="PedelecId")
	private Pedelec pedelec;

	/**
	 * A foreign key which represents the Employee who reported the damage.
	 */
	@Transient
	@ManyToOne
	@JoinColumn(name = "EmployeeId", referencedColumnName="EmployeeId")
	private Employee employee;

	/**
	 * The damage reason / part.
	 */
	@Column(name = "DamageReason")
	private String damageReason;

	/**
	 * The damage description.
	 */
	@Column(name = "DamageDescription")
	private String damageDescription;

	/**
	 * The time that the damage was reported.
	 */
	@Column(name = "DateReported")
	private long dateReported;

	/**
	 * The time that the damage was repaired.
	 */
	@Column(name = "DateRepaired")
	private long dateRepaired;

	/**
	 * The id of the pedelec having the damage. It is not stored in the
	 * database.
	 */
	private int pedelecID;

	/**
	 * The id of the employee who reported the damage. It is not stored in the
	 * database.
	 */
	private int employeeID;

	public int getDamageReportID() {
		return damageReportId;
	}

	public void setDamageReportID(int idDamageReport) {
		this.damageReportId = idDamageReport;
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

	public String getDamageReason() {
		return damageReason;
	}

	public void setDamageReason(String damageReason) {
		this.damageReason = damageReason;
	}

	public String getDamageDescription() {
		return damageDescription;
	}

	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}

	public long getDateReported() {
		return dateReported;
	}

	public void setDateReported(long dateReported) {
		this.dateReported = dateReported;
	}

	public long getDateRepaired() {
		return dateRepaired;
	}

	public void setDateRepaired(long dateRepaired) {
		this.dateRepaired = dateRepaired;
	}

	public DamageReport() {
	}

	/**
	 * Constructor of class DamageReport. This constructor is used when we
	 * pre-fill the database with some data.
	 */
	public DamageReport(Pedelec pedelec, Employee employee,
			String damageReason, String damageDescription, long reportingDate,
			long repairedDate) {
		this.pedelec = pedelec;
		this.employee = employee;
		this.pedelecID = pedelec.getPedelecID();
		this.employeeID = employee.getEmployeeId();
		this.damageReason = damageReason;
		this.damageDescription = damageDescription;
		this.dateReported = reportingDate;
		this.dateRepaired = repairedDate;
	}

	/**
	 * Constructor of the class DamageReport. This constructor is used in the
	 * damage report resource, when a new damage report is created and shall be
	 * connected to a pedelec and an employee in the database.
	 */

	public DamageReport(int pedelecId, int employeeId, String damageReason,
			String damageDescription, long reportingDate) {
		this.pedelecID = pedelecId;
		this.employeeID = employeeId;
		this.damageReason = damageReason;
		this.damageDescription = damageDescription;
		this.dateReported = reportingDate;

		EntityManagerFactory factory = EntityManagerFactorySingleton
				.getSharedInstance();
		EntityManager em = factory.createEntityManager();

		Query q = em
				.createQuery("select p from Pedelec p WHERE p.pedelecId = '"
						+ pedelecId + "'");
		@SuppressWarnings("unchecked")
		List<Pedelec> pedelec = q.getResultList();
		this.pedelec = pedelec.get(0);

		q = em.createQuery("select e from Employee e WHERE e.employeeId = '"
				+ employeeId + "'");
		@SuppressWarnings("unchecked")
		List<Employee> employee = q.getResultList();
		this.employee = employee.get(0);
	}

	public int getPedelecID() {
		return pedelecID;
	}

	public void setPedelecID(int pedelecID) {
		this.pedelecID = pedelecID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
		EntityManagerFactory factory = EntityManagerFactorySingleton
				.getSharedInstance();
		EntityManager em = factory.createEntityManager();

		Query q = em
				.createQuery("select e from Employee e WHERE e.employeeId = '"
						+ employeeID + "'"); // search the db for the employee
												// with the specified id
		@SuppressWarnings("unchecked")
		List<Employee> employee = q.getResultList();
		setEmployee(employee.get(0)); // update the class variable employee

	}

	public void setOnlyEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	@Override
	public String toString() {
		return "DamageReport [damageReportID=" + damageReportId + ", pedelec="
				+ pedelec + ", employee=" + employee + ", damageReason="
				+ damageReason + ", damageDescription=" + damageDescription
				+ ", reportingDate=" + dateReported + ", repairedDate="
				+ dateRepaired + ", pedelecID=" + pedelecID + ", employeeID="
				+ employeeID + "]";
	}

}
