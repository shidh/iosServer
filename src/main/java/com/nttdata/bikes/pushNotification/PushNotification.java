package com.nttdata.bikes.pushNotification;

import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.DeviceToken;
import com.nttdata.bikes.entities.Employee;
import com.nttdata.bikes.infrastructure.LoggingManager;
import com.nttdata.bikes.restserver.DeviceTokenResource;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * This class is used in order to send the Apple PushNotification service. 
 * We use the JavaPNS framework for this.
 * It is executed in the ThreadedPushNotification class.
 * @author Isabel Max
 *
 */

public class PushNotification
{

  private static Logger logger = LoggingManager.getLogger();

  /**
   * This method is called, when a PushNotification has to be sent in the server.
   * It sends the given message and payload to the employee with the given employeeId.
   * @param message
   * @param payload
   * @param employeeId
   */
  public static void send(String message, String payload, int employeeId)
  {
    PushNotification.doDatabaseBasedPushWithQuery(message, payload, "select e from Employee e WHERE e.employeeId = '"
                                                                    + employeeId + "'");
  }

  /**
   * This method sends a pushNotifications to all employees (we do not use this method at the moment)
   * @param message
   * @param payload
   */
  public static void sendToAllDevices(String message, String payload)
  {
    PushNotification.doDatabaseBasedPushWithQuery(message, payload, "SELECT e FROM Employee e");
  }

  /**
   * This method is called, when you have a deviceToken to which device you want to push the notification.
   * @param message
   * @param payload
   * @param deviceToken
   */
  public static void sendWithDeviceToken(String message, String payload, String deviceToken)
  {
    // send notification
    List<String> deviceTokens = new ArrayList<String>();
    deviceTokens.add(deviceToken);
    ThreadedPushNotification notification = new ThreadedPushNotification(message, payload, deviceTokens);
    notification.start();
  }

  /**
   * In this method we execute the query, which we insert as param and get the device tokens from the employees
   * and start the ThreadedPushNotification with the deviceToken list.
   * @param message
   * @param payload
   * @param query
   */
  // push call for threaded push
  private static void doDatabaseBasedPushWithQuery(String message, String payload, String query)
  {
    // query DB
    EntityManagerFactory factory;
    factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      em.getTransaction().begin();
      Query q = em.createQuery(query);
      @SuppressWarnings("unchecked")
      List<Employee> resultList = q.getResultList();
      List<String> tokens = new ArrayList<String>();

      Employee requestedEmployee = resultList.get(0);
      ArrayList<DeviceToken> deviceTokensOfEmployee =
          new ArrayList<DeviceToken>(DeviceTokenResource.getDeviceTokensforEmployee(requestedEmployee.getEmployeeId()));
      if (deviceTokensOfEmployee != null && !deviceTokensOfEmployee.isEmpty()) {
        for (DeviceToken deviceToken : deviceTokensOfEmployee) {
          String tokenString = deviceToken.getTokenString();
            tokens.add(tokenString);
          logger.info("token is " + deviceToken);
        }
      }

      if (!(tokens.isEmpty())) {
        // send notification
        ThreadedPushNotification notification = new ThreadedPushNotification(message, payload, tokens);
        notification.start();
      }

      em.getTransaction().commit();
    }
    finally {
      em.close();
    }
  }

}
