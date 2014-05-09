package com.nttdata.bikes.restserver;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.Pedelec;
import com.nttdata.bikes.entities.PedelecStation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * This class is used when "/pedelecstation" is appended to the REST URL of the server.
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Path("pedelecStations")
public class PedelecStationResource
{

  /**
   * This function is called when "\getallpedelecstations" is appended to the URL in which this class responds.
   * It is used in order to return all the pedelec stations in the database.
   * @return A JSON Message with the information for all the reservations.
   */

  @GET
  @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
  public Response getAllStations()
  {
    List<PedelecStation> stationList = getAllStationsFromDatabase(); // get a list of all the stations
    if (stationList != null) { // if the list is not empty
      List<PedelecStation> stationArrayList = new ArrayList<PedelecStation>(stationList); // initialize an ArrayList
      GenericEntity<List<PedelecStation>> entity = new GenericEntity<List<PedelecStation>>(stationArrayList) {};
      return Response.ok(entity).build();
    }
    else {
      return Response.status(Status.NO_CONTENT).build();
    }
  }
  
     /**
     * This function is called when "/{pedelecStationId}/pedelecs" is
     * appended to the URL in which this class responds. xxx represends the id
     * of the station.
     *
     * @return A JSON Message containing information about the latest
     * reservation.
     */
    @GET
    @Path("{pedelecStationId}/pedelecs")
    @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
    public Response getPedelecsFromStation(@PathParam("pedelecStationId") int stationId) {
        PedelecResource pedelecResource = new PedelecResource();
        List<Pedelec> pedelecArrayList = pedelecResource.getPedelecsAtStation(stationId);
        if (!pedelecArrayList.isEmpty()) { // If there are pedelecs parked in this station
            for (Pedelec pedelec : pedelecArrayList) {
                pedelec = pedelecResource.setReservationStatusAndEmployeeId(pedelec);
                pedelec = pedelecResource.setBatteryStatus(pedelec);
            }
            GenericEntity<List<Pedelec>> entity = new GenericEntity<List<Pedelec>>(pedelecArrayList) {
            };
            return Response.ok(entity).build();
        } else {
            return Response.status(Status.NO_CONTENT).build();
        }
    }

  /**-
   * This function issues a query to the database to get a list with all stations
   */

  @SuppressWarnings("unchecked")
  private List<PedelecStation> getAllStationsFromDatabase()
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();;
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      Query q = em.createQuery("select p from PedelecStation p");
      return q.getResultList();
    }
    finally {
      em.close();
    }
  }

}
