package com.nttdata.bikes.restserver;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.DamageReport;
import com.nttdata.bikes.infrastructure.LoggingManager;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 * This class is used when "/damageReports" is appended to the REST URL of the
 * server.
 *
 * @author Gerhard Henning
 */

@Path("damageReports")
public class DamageReportResource {

    static Logger logger = LoggingManager.getLogger();
    static EntityManagerFactory factory;

    /**
     * This function is called when nothing is appended to the URL in which this
     * class responds. It is used in order to return all the damage reports in the
     * database.
     *
     * @return A JSON Message with the information for all the damage reports.
     */
    @GET
    @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
    public Response getAllDamageReports() {
        List<DamageReport> damageReportList = getAllDamageReportsFromDatabase();
        if (damageReportList != null && !damageReportList.isEmpty()) { // if the list is not empty
            List<DamageReport> damageReportArrayList = new ArrayList<DamageReport>(damageReportList); // initialize an ArrayList
            GenericEntity<List<DamageReport>> entity = new GenericEntity<List<DamageReport>>(damageReportArrayList) {
            };
            return Response.ok(entity).build();
        } else {
            return Response.status(Status.NO_CONTENT).build();
        }
    }

    /**
     * This function inserts a new DamageReport in the database.
     */
    @POST
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDamageReport(DamageReport damageReportFromClient) {
  
            // if the client sends no damage report to update or post return empty response
            if (damageReportFromClient == null) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            factory = EntityManagerFactorySingleton.getSharedInstance();
            EntityManager em = factory.createEntityManager();
            DamageReport damageReport = null;

        try {
            em.getTransaction().begin();

            boolean pedelecIdExists = PedelecResource.pedelecIdExists(damageReportFromClient.getPedelecID());
            boolean employeeIdExists = EmployeeResource.employeeIdExists(damageReportFromClient.getEmployeeID());

            if (pedelecIdExists && employeeIdExists) {
                damageReport = new DamageReport(damageReportFromClient.getPedelecID(), damageReportFromClient.getEmployeeID(),
                        damageReportFromClient.getDamageReason(), damageReportFromClient.getDamageDescription(),
                        System.currentTimeMillis());
                logger.info("New damage report created: "
                        + damageReport.toString());
                em.persist(damageReport);
            }
            // executing the changes in the database
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        factory.close();
        // return updated/new reservations
        List<DamageReport> damageReportArrayList = new ArrayList<DamageReport>();
        damageReportArrayList.add(damageReport);
        GenericEntity<List<DamageReport>> entity = new GenericEntity<List<DamageReport>>(damageReportArrayList) {};
        return Response.ok(entity).build();
    }

     /**
     * This function issues a query to the database in order to retrieve all the
     * reservations.
     *
     * @return A List with all the Reservation Objects.
     */
    @SuppressWarnings("unchecked")
    private List<DamageReport> getAllDamageReportsFromDatabase() {
        EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
        EntityManager em = factory.createEntityManager();

        try {
            // Read the existing entries and write to console
            Query q = em.createQuery("select r from DamageReport r");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
