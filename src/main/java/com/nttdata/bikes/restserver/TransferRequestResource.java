package com.nttdata.bikes.restserver;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.Reservation;
import com.nttdata.bikes.entities.TransferRequest;
import com.nttdata.bikes.infrastructure.LoggingManager;
import com.nttdata.bikes.pushNotification.PushNotification;
import com.sun.jersey.api.client.ClientResponse.Status;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 * This class is used when "/transferrequests" is appended to the RESTURL of the server
 * @author Stefanos Stamogiorgos
 */
@Path("transferRequests")
public class TransferRequestResource
{

  private static Logger logger = LoggingManager.getLogger();
  private ReservationResource reservationResource = new ReservationResource();
  private Reservation initialReservation;

  /**
   * This function is called when nothing is appended to the URL
   * It creates a new transfer request in the database and sends a notification to user.
   * If there is any error it responds to the app with a String containing an error description
   * @return A response in json
   */

  @POST
  @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createRequest(TransferRequest transferRequestFromClient)
  {
      int requesterId = transferRequestFromClient.getRequesterId();
      int holderId = transferRequestFromClient.getHolderId();
    if (requesterId != holderId) {
      if (EmployeeResource.employeeIdExists(requesterId) && (EmployeeResource.employeeIdExists(holderId))) { // both Ids exists
        if (!(getTransferRequestsForEmployee(holderId) != null && !getTransferRequestsForEmployee(holderId).isEmpty())) { // HolderId has no PendingRequest
          if (holderEmployeeHasReservation(holderId)) {
            if (requesterEmployeeHasAlreadyRequested(requesterId)) {
              logger.info("Employee " + requesterId + " has already requested");
              return Response.ok("You are not allowed to request more than one persons").build();
            }
            else {
              if (requesterEmployeeHasReservation(requesterId)) {
                logger.info("Employee " + requesterId + " has already a reservation");
                return Response.ok("You already have an active reservation").build();
              }
              else {
                createNewTransferRequest(requesterId, holderId, initialReservation.getReservationId()); // Add a new entry in the database
                String employeeName = EmployeeResource.getNameofEmployee(requesterId);
                PushNotification.send("Hello! "
                                          + employeeName
                                          + " wants to have your reservation. Please open Bikes in order to answer this request.",
                                      "payload",
                                      holderId); // Send the push notification
                logger.info("New Transfer Request Created for" + employeeName);
                return Response.status(Status.CREATED).build();
              }
            }
          }
          else {
            logger.info("Employee " + holderId + " has no reservation");
            return Response.ok("There is not such a reservation").build(); // There is no reservation specified for this employee
          }
        }
        else {
          logger.info("Employee " + holderId + " has already a pending request");
          return Response.ok("There is another pending request for this employee").build();
        }
      }
      else {
        logger.info("There are no employees with id " + holderId + " or " + requesterId);
        return Response.ok("There is not such employee").build(); // There are not such employees
      }
    }
    else {
      logger.info("You cannnot ask for your own reservation");
      return Response.ok("You cannot ask for your own reservation").build();
    }
  }

  /**
   * This function is called when method DELETE is called on this path
   * @param accepted if the transfer of the reservation was accepted
   * @return ACCEPTED in success , NO_CONTENT if request does not exist
   */

    @DELETE
    @Path("{reservationId}")
    public Response removePendingRequest(@PathParam(value = "reservationId") int reservationId, @QueryParam(
            value = "accepted") boolean accepted) {
        List<TransferRequest> transferRequestsList = getTransferRequestsWithReservationId(reservationId);
        if ((transferRequestsList != null && !transferRequestsList.isEmpty())) { // If there are transferrequests for this reservation
            EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
            EntityManager em = factory.createEntityManager();

            TransferRequest transferRequest = transferRequestsList.get(0);
            if (transferRequest != null) {
                try {
                    int holderId = transferRequest.getHolderId();
                    int requesterId = transferRequest.getRequesterId();
                    if (!accepted) { // if the holderEmployee did not accept the passing of the reservation
                        PushNotification.send("We are sorry, but " + EmployeeResource.getNameofEmployee(holderId)
                                + " doesn't want to pass his reservation to you.", "payload", requesterId); // Send a push notification to inform the other employee
                    }
                    else { // if the holderEmployee did accept the passing of the reservation
                        // TODO: update reservation 
                        PushNotification.send("Congratulations, " + EmployeeResource.getNameofEmployee(holderId)
                                + " passed his/her reservation to you.", "payload", requesterId);
                    }
                    // delete transferRequest from the database either way
                    em.getTransaction().begin();
                    em.remove(em.merge(transferRequest)); 
                    em.getTransaction().commit();
                    logger.info("Transfer request for reservation No." + transferRequest.getReservationId() + " was deleted");
                    return Response.status(Status.OK).build();
                } finally {
                    em.close();
                }
            }
        }
        logger.info("There is no Transfer Request with ReservationId " + reservationId);
        return Response.status(Status.NOT_FOUND).build();
    }

  @SuppressWarnings("unchecked")
  public List<TransferRequest> getTransferRequestsForEmployee(int holderId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      Query q = em.createQuery("select e from TransferRequest e WHERE e.holderId = '" + holderId + "'");

      return q.getResultList();
    }
    finally {
      em.close();
    }
  }

  private boolean holderEmployeeHasReservation(int holderId)
  {
    List<Reservation> currentReservationListEmployee = reservationResource.getCurrentReservationFromEmployee(holderId);
    if (currentReservationListEmployee != null && !currentReservationListEmployee.isEmpty()) {
      initialReservation = currentReservationListEmployee.get(0);
      return true;
    }
    else {
      return false;
    }
  }

  private boolean requesterEmployeeHasReservation(int requesterId)
  {
    List<Reservation> currentReservationsListEmployee =
        reservationResource.getCurrentReservationFromEmployee(requesterId);
    return (currentReservationsListEmployee != null && !currentReservationsListEmployee.isEmpty());
  }

  public static boolean requesterEmployeeHasAlreadyRequested(int requesterId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      Query q = em.createQuery("select e from TransferRequest e WHERE e.requesterId = '" + requesterId + "'");
      return ((q.getResultList() != null && !q.getResultList().isEmpty()));
    }
    finally {
      em.close();
    }
  }

  @SuppressWarnings("unchecked")
  public static List<TransferRequest> getTransferRequestsWithReservationId(int reservationId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      Query q = em.createQuery("select e from TransferRequest e WHERE e.reservationId = '" + reservationId + "'");
      return q.getResultList();
    }
    finally {
      em.close();
    }
  }

  @SuppressWarnings("unchecked")
  public static List<TransferRequest> getAllTransferRequestsFromDatabase()
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      Query q = em.createQuery("select e from TransferRequest e");
      return q.getResultList();
    }
    finally {
      em.close();
    }
  }

  public static void createNewTransferRequest(int RequesterId, int holderId, int ReservationId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      em.getTransaction().begin();

      em.persist(new TransferRequest(RequesterId, holderId, ReservationId));
      em.getTransaction().commit();
    }
    finally {
      em.close();
    }
  }

}
