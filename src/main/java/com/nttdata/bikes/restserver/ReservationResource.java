package com.nttdata.bikes.restserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.Reservation;
import com.nttdata.bikes.infrastructure.LoggingManager;
import com.nttdata.bikes.pushNotification.PushNotification;

/**
 * This class is used when "/reservations" is appended to the REST URL of the
 * server.
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Path("reservations")
public class ReservationResource
{

  static Logger logger = LoggingManager.getLogger();
  static EntityManagerFactory factory;

  /**
   * This function is called when nothing is appended to the URL
   * in which this class responds. It is used in order to return all the
   * reservations in the database.
   * @return A JSON Message with the information for all the reservations.
   */

  @GET
  @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
  public Response getAllReservations()
  {
    List<Reservation> reservationList = getAllReservationsFromDatabase(); // get a list of all the stations
    if (reservationList != null && !reservationList.isEmpty()) { // if the list is not empty
      List<Reservation> reservationArrayList = new ArrayList<Reservation>(reservationList); // initialize an ArrayList
      GenericEntity<List<Reservation>> entity = new GenericEntity<List<Reservation>>(reservationArrayList) {};
      return Response.ok(entity).build();
    }
    else {
      return Response.status(Status.NO_CONTENT).build();
    }
  }


  /**
   * This function issues a query to the database in order to retrieve all the
   * reservations.
   *
   * @return A List with all the Reservation Objects.
   */

  @SuppressWarnings("unchecked")
  public List<Reservation> getAllReservationsFromDatabase()
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      Query q = em.createQuery("select r from Reservation r");
      return q.getResultList();
    }
    catch(Exception e) {
        return null;
    }
    finally {
      em.close();
    }
  }

  /**
   * This function inserts a new Reservation in the database.
   */

  @POST
  @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postAndUpdateReservations(Reservation reservationFromClient)
  {
    // if the client sends no reservation to update or post return empty response
    if (reservationFromClient == null) {
      return Response.status(Status.NO_CONTENT).build();
    }

    // the entity manager is the manager of persisting the data in the database
    factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();
    Reservation reservation = null;

      try {
          em.getTransaction().begin();

          logger.info("Request with reservation: " + reservationFromClient.toString());

          int reservationId = reservationFromClient.getReservationId();
          logger.info("Request with reservationId: " + reservationId);
          Query q = em.createQuery("select r from Reservation r WHERE r.reservationId = '" + reservationId + "'");

          @SuppressWarnings("unchecked")
          List<Reservation> queryResult = q.getResultList();

          if (reservationFromClient != null) {
              if (queryResult == null || queryResult.isEmpty()) {
                  // reservation is new

                  List<Reservation> currentReservationListPedelec
                          = getCurrentReservation(reservationFromClient.getPedelec_id());
                  List<Reservation> currentReservationListEmployee
                          = getCurrentReservationFromEmployee(reservationFromClient.getEmployee_id());
                  boolean pedelecIsReserved
                          = currentReservationListPedelec != null && !currentReservationListPedelec.isEmpty();
                  boolean employeeHasAlreadyReserved
                          = currentReservationListEmployee != null && !currentReservationListEmployee.isEmpty();

                  boolean pedelecIdExists = PedelecResource.pedelecIdExists(reservationFromClient.getPedelec_id());
                  boolean employeeIdExists = EmployeeResource.employeeIdExists(reservationFromClient.getEmployee_id());

                  if (!pedelecIsReserved && !employeeHasAlreadyReserved && pedelecIdExists && employeeIdExists) { // pedelec not reserved yet and employee has no reservation at the same time
                      reservation
                              = new Reservation(reservationFromClient.getPedelec_id(),
                                      reservationFromClient.getEmployee_id(),
                                      reservationFromClient.getStartDateTime(),
                                      reservationFromClient.getEndDateTime(),
                                      reservationFromClient.getTotalKm(),
                                      reservationFromClient.getTotalTravelTime(),
                                      reservationFromClient.getAverageSpeed(),
                                      reservationFromClient.getMaxSpeed());
                      logger.info("New reservation created: " + reservation.toString());
                      em.persist(reservation);
                  } else if (pedelecIsReserved) {
              // the pedelec is already reserved and it cannot be
                      // reserved again
                      reservation = getCurrentReservation(reservationFromClient.getPedelec_id()).get(0);
                  } else if (employeeHasAlreadyReserved) {
                      return Response.status(Status.NO_CONTENT).build();
                  }
              } else if (queryResult.get(0).getEndDateTime() != reservationFromClient.getEndDateTime()
                      || queryResult.get(0).getEmployee_id() != reservationFromClient.getEmployee_id()) {
            // only reservation end date and reservation employee can be
                  // changed
                  // reservation needs to be updated in DB
                  reservation = queryResult.get(0);
                  if (reservation.getEndDateTime() != reservationFromClient.getEndDateTime()) {
                      reservation.setEndDateTime(reservationFromClient.getEndDateTime());
                  }
                  if (reservation.getEmployee_id() != reservationFromClient.getEmployee_id()) {
                      int formerEmployeeId = reservation.getEmployee_id();

                      reservation.setEmployee_id(reservationFromClient.getEmployee_id());

                      String employeeName = EmployeeResource.getNameofEmployee(formerEmployeeId);
                      PushNotification.send(employeeName + " has passed his reservation to you.",
                              "test",
                              reservationFromClient.getEmployee_id());
                  }
                  em.merge(reservation);
              } else {
                  // reservation is old - do nothing
                  reservation = queryResult.get(0);
              }
          }
          // executing the changes in the database
          em.getTransaction().commit();
      }
    finally {
      em.close();
    }
    factory.close();
    // return updated/new reservations
    List<Reservation> reservationArrayList = new ArrayList<Reservation>();
    reservationArrayList.add(reservation);
    GenericEntity<List<Reservation>> entity = new GenericEntity<List<Reservation>>(reservationArrayList) {};
    return Response.ok(entity).build();
  }

  /**
   * A reservation is active, if the end date is in the future or if it's
   * null.
   */

  public List<Reservation> getCurrentReservation(int pedelecId)
  {
    List<Reservation> reservationList = getAllReservationsFromDatabase(); // get a list of all the reservations which are currently active
    List<Reservation> returnedReservationList = new ArrayList<Reservation>();
    for (Reservation reservation : reservationList) {
      // if there is a pedelec to the reservation and if the pedelec id is
      // equal to the inserted pedelecId
      // logger.info("Reservation: " + reservation.toString());
      if (reservation.getPedelec_id() == pedelecId) {
        logger.info("pedelecId equals inserted: " + reservation.toString());
        // if there is no end date of the reservation, or if the end
        // time is in the future, the reservation is active at this
        // moment
        logger.info("\nCurrent Time in Date: " + new Date() + "Current Time in s: " + new Date().getTime() / 1000
                    + "\nReservation Time in Date: " + new Date(reservation.getEndDateTime())
                    + "Reservation Time in ms: " + reservation.getEndDateTime());
        if (reservationIsActive(reservation)) {
          logger.info("CurrentReservation found!: " + reservation.toString());
          returnedReservationList.add(reservation);
        }
      }
    }
    return returnedReservationList;
  }

  public List<Reservation> getCurrentReservationFromEmployee(int employeeId)
  {
    List<Reservation> reservationList = getAllReservationsFromDatabase(); // get a list of all the stations
    List<Reservation> returnedReservationList = new ArrayList<Reservation>();
    for (Reservation reservation : reservationList) {
      // if there is an employee to the reservation and if the employee id
      // is equal to the inserted employeeId
      logger.info("Reservation: " + reservation.toString());
      if (reservation.getEmployee_id() == employeeId) {
        logger.info("employeeId equals inserted: " + reservation.toString());
        // if there is no end date of the reservation, or if the end
        // time is in the future, the reservation is active at this
        // moment
        if (reservation.getEndDateTime() == 0 || reservation.getEndDateTime() > new Date().getTime()) {
          logger.info("CurrentReservation found!: " + reservation.toString());
          returnedReservationList.add(reservation);
        }
      }
    }
    return returnedReservationList;
  }

  /**
   * Get the reservation with the specified id
   * @param reservationId An int with the reservation Id
   * @return A List which always has at maximum one element.
   */

  @SuppressWarnings("unchecked")
  public static List<Reservation> getReservationswithId(int reservationId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      Query q = em.createQuery("select r from Reservation r WHERE r.reservationId = '" + reservationId + "'");

      return q.getResultList();
    }
    finally {
      em.close();
    }
  }

  public static boolean reservationIsActive(Reservation reservation)
  {
    return reservation.getEndDateTime() == 0 || reservation.getEndDateTime() > new Date().getTime();
  }

}
