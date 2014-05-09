package com.nttdata.bikes.test;

import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.Employee;
import com.nttdata.bikes.entities.Pedelec;
import com.nttdata.bikes.entities.PedelecStation;
import com.nttdata.bikes.entities.Reservation;
import com.nttdata.bikes.infrastructure.LoggingManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


/**
 * Servlet implementation class JPAMappingTest.
 * @author Isabel Max
 */
public class JPAMappingTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggingManager.getLogger();

	private static EntityManagerFactory factory;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JPAMappingTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// MIME-Typ der Antwort bestimmen
		response.setContentType("text/html");

		// Writer holen
		PrintWriter out = response.getWriter();

		// Print HTML test page
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Test</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h1>Hallo Welt</h1>");


		factory = EntityManagerFactorySingleton.getSharedInstance();
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		
		/* Read entries for the pedelecs*/
		Query q = em.createQuery("select p from Pedelec p");
		List<Pedelec> pedelecList = q.getResultList();
		
		for (Pedelec ped : pedelecList) {
			out.println(ped + "<br>");
		}
		out.println("Size: " + pedelecList.size() + "<br>");
		
		/* Read entries for the employees*/
		q = em.createQuery("select p from Employee p");
		List<Employee> employeeList = q.getResultList();
		
		for (Employee emp : employeeList) {
			out.println(emp + "<br>");
		}
		out.println("Size: " + employeeList.size() + "<br>");
		
		/* Read entries for the pedelecs*/
		q = em.createQuery("select p from PedelecStation p");
		List<PedelecStation> pedStationList = q.getResultList();
		
		for (PedelecStation pedStation : pedStationList) {
			out.println(pedStation + "<br>");
		}
		out.println("Size: " + pedStationList.size() + "<br>");
		
		/* Read entries for Reservations*/
		q = em.createQuery("select p from Reservation p");
		List<Reservation> reservList = q.getResultList();
		
		for (Reservation reservation : reservList) {
			out.println(reservation + "<br>");
		}
		out.println("Size: " + reservList.size() + "<br>");

		em.close();

		
		out.println("</body>");
		out.println("</html>");
		
		logger.info("JPA Mapping was printed");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
