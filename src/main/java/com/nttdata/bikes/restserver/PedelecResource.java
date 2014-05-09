package com.nttdata.bikes.restserver;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.Pedelec;
import com.nttdata.bikes.entities.Reservation;
import com.nttdata.bikes.infrastructure.LoggingManager;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 * This class is used when "/pedelecs" is appended to the REST URL of the
 * server.
 *
 * @author Isabel Max, Stefanos Stamogiorgos
 */
@Path("pedelecs")
public class PedelecResource {

    private static Logger logger = LoggingManager.getLogger();

    ReservationResource reservationResource = new ReservationResource();

    /**
     * This function is called when nothing is appended to the URL in which this
     * class responds. It is used in order to return all the pedelecs in the
     * database.
     *
     * @return A JSON Message with the information for all the pedelecs.
     */
    @GET
    @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
    public Response getAllPedelecs() {
        List<Pedelec> pedelecList = getAllPedelecsFromDatabase(); // get a list of all the pedelecs
        if (pedelecList != null) { // if the list is not empty
            for (Pedelec pedelec : pedelecList) {
                pedelec = setReservationStatusAndEmployeeId(pedelec);
                pedelec = setBatteryStatus(pedelec);
            }
            List<Pedelec> pedelecArrayList = new ArrayList<Pedelec>(pedelecList); // initialize an ArrayList
            GenericEntity<List<Pedelec>> entity = new GenericEntity<List<Pedelec>>(pedelecArrayList) {
            };
            return Response.ok(entity).build();
        } else {
            return Response.status(Status.NO_CONTENT).build();
        }
    }
    
     /**
   * This function is called when
   * "{pedelecId}/reservations" is appended to the URL in
   * which this class responds. pedelecId represents the id of the pedelec. It is
   * used in order to return the current reservation of a Pedelec.
   *
   * @return A JSON Message containing information about the latest
   *         reservation.
   */

  @GET
  @Path("{pedelecId}/reservations")
  @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
  public Response getCurrentReservationOfPedelec(@PathParam(value = "pedelecId") int pedelecId)
  {
    logger.info("getCurrentReservationOfPedelec called with pedelecId " + pedelecId);
    ReservationResource reservationResource = new ReservationResource();
    List<Reservation> returnedReservationList = reservationResource.getCurrentReservation(pedelecId);
    if (returnedReservationList == null || returnedReservationList.isEmpty()) {// if there is no reservation currently for the Pedelec, return an empty response
      return Response.status(Status.NO_CONTENT).build();
    }
    List<Reservation> returnedReservationArrayList = new ArrayList<Reservation>(returnedReservationList);
    GenericEntity<List<Reservation>> entity = new GenericEntity<List<Reservation>>(returnedReservationArrayList) {};
    return Response.ok(entity).build();
  }


    public ArrayList<Pedelec> getPedelecsAtStation(int stationId) {
        List<Pedelec> pedelecList = getAllPedelecsFromDatabase(); // get a list of all the pedelecs
        List<Pedelec> pedelecArrayList = new ArrayList<Pedelec>();
        for (Pedelec pedelec : pedelecList) { // for every pedelec in the list
            if (pedelec.getStationId() == stationId) { // if the stationId of the pedelec matches the requested stationId
                pedelec = setReservationStatusAndEmployeeId(pedelec);
                pedelecArrayList.add(pedelec); // add this pedelec to a new arraylist
            }
        }
        return (ArrayList<Pedelec>) pedelecArrayList;
    }

    /**
     * This method returns the correct reservation status of the time of
     * request.
     */
    public Pedelec setReservationStatusAndEmployeeId(Pedelec pedelec) {
        if (reservationResource.getCurrentReservation(pedelec.getPedelecID()) != null
                && !reservationResource.getCurrentReservation(pedelec.getPedelecID()).isEmpty()) {
            pedelec.setReserved(true);
            pedelec.setReservedByEmployeeWithId(reservationResource.getCurrentReservation(pedelec.getPedelecID())
                    .get(0)
                    .getEmployee_id());
        } else {
            pedelec.setReserved(false);
        }
        return pedelec;
    }

    /**
     * This method should request the Pedelec and ask for the battery status. If
     * the request fails, the battery status will set to -1 and in the
     * application it is shown as "out of order".
     *
     * It is not correctly implemented yet, because the pedelecs do not exist
     * until now. For this reason it will be set randomly.
     */
    public Pedelec setBatteryStatus(Pedelec pedelec) {
        int randomIntModulo100 = (int) (Math.random() * 100);
        //it is 10 % propability that the battery status cannot be retrieved
        if (randomIntModulo100 < 10) {
            pedelec.setChargingLevel(-1);
            logger.error("Cannot retrieve battery info from pedelec with ID: " + pedelec.getPedelecID());
            //TODO: find better solution
            pedelec.setChargingStatus(false);
        } else {
            //TODO: do we have to think about logic? Perhaps that it is not possible that the charging level sinks until the next request, but the charging level is true?
            pedelec.setChargingLevel(randomIntModulo100);
            // it is 50% propability that the charging status is true
            if (randomIntModulo100 % 2 == 0) {
                pedelec.setChargingStatus(true);
            } else {
                pedelec.setChargingStatus(false);
            }
        }
        return pedelec;
    }

    /**
     * This function issues a query to the database to get all Pedelecs
     */
    @SuppressWarnings("unchecked")
    public List<Pedelec> getAllPedelecsFromDatabase() {
        EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
        EntityManager em = factory.createEntityManager();

        try {
            // Read the existing entries and write to console
            Query q = em.createQuery("select p from Pedelec p");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Check if there is a Pedelec with the respective Id
     *
     * @param pedelecId The Id of the pedelec we are about to search for
     * @return True if the Id exists otherwise false
     */
    public static boolean pedelecIdExists(int pedelecId) {
        EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
        EntityManager em = factory.createEntityManager();

        try {
            // Read the existing entries and write to console
            Query q = em.createQuery("select p from Pedelec p WHERE p.pedelecId = '" + pedelecId + "'");

            return ((q.getResultList() != null && !q.getResultList().isEmpty()));
        } finally {
            em.close();
        }
    }

}
