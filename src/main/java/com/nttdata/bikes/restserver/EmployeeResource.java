package com.nttdata.bikes.restserver;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.DeviceToken;
import com.nttdata.bikes.entities.Employee;
import com.nttdata.bikes.entities.TransferRequest;
import com.nttdata.bikes.infrastructure.LoggingManager;
import static com.nttdata.bikes.restserver.DeviceTokenResource.deviceTokenExists;
import static com.nttdata.bikes.restserver.DeviceTokenResource.factory;
import com.sun.jersey.api.client.ClientResponse;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;

/**
 * This class is used when "/employees" is appended to the REST URL of the server.
 * @author Isabel Max, Stefanos Stamogiorgos
 */

@Path("employees")
@XmlRootElement
public class EmployeeResource
{

  private static Logger logger = LoggingManager.getLogger();

  /**
   * This function is called when nothing is appended to the URL in which this class responds.
   * It is used in order to return all the employees in the database, or filter by username and password.
   * @param username the username of the user
   * @param password An md5 hash value
   * @return A JSON Message with the information for all the employees.
   */

  @GET
  @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
  public Response getEmployees(@QueryParam(value = "username") String username, @QueryParam(
            value = "password") String password) {
      // if no parameters were given, get all employees
        if (username == null && password == null) {
            List<Employee> employeeList = getAllEmployeesFromDatabase(); // get a list of all the employees
            if (employeeList != null) { // if the list is not empty
                for (Employee employee : employeeList) {
                    if (employee.getMobileNo() == null) {
                        logger.info("Cannot retrieve mobile number of user " + employee.getName() + " " + employee.getSurname());
                    }
                }
                List<Employee> employeeArrayList = new ArrayList<Employee>(employeeList); // initialize an ArrayList
                logger.info("All employees requested.");
                GenericEntity<List<Employee>> entity = new GenericEntity<List<Employee>>(employeeArrayList) {
                };
                return Response.ok(entity).build();
            } else {
                return Response.status(Status.NO_CONTENT).build();
            }
        } else if(!(username == null && password == null)) {
            Employee requestedEmployee = getOneEmployeewithUsernameandPassword(username, password);
            if (requestedEmployee != null) {
                GenericEntity<Employee> entity = new GenericEntity<Employee>(requestedEmployee) {
                };
                return Response.ok(entity).build();
            } else {
                return Response.status(Status.NO_CONTENT).build();
            }
        }
        // only one parameter was given
        return Response.status(Status.BAD_REQUEST).build();
    }
  
    /**
     * This function lists all deviceTokens for the specified employee
     * the database.
     * @param employeeId the employee's id
     * @return a list of all deviceTokens for the specified employee
     */
    @GET
    @Path("{employeeId}/deviceTokens")
    @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
    public Response getEmployeeDeviceTokens(@PathParam("employeeId") int employeeId) {
        List<DeviceToken> deviceTokens = DeviceTokenResource.getDeviceTokensforEmployee(employeeId);
        // if no device tokens exist for the employee
        if(deviceTokens.isEmpty()) {
            return Response.status(ClientResponse.Status.NO_CONTENT).build();
        }
        List<DeviceToken> deviceTokensArrayList = new ArrayList<DeviceToken>(deviceTokens);
        
        GenericEntity<List<DeviceToken>> entity = new GenericEntity<List<DeviceToken>>(deviceTokensArrayList) {};
        return Response.ok(entity).build();

    }
    
          /**
     * This function inserts a new deviceToken for the specified employee into
     * the database.
     *
     * @return A JSON Message of failure or success.
     */
    @POST
    @Path("{employeeId}/deviceTokens")
    @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTokenForId(@PathParam("employeeId") int employeeId, String deviceTokenFromClient) {

        // commented out because of problems - refactoring needed!
        //if (employeeIdExists(deviceTokenFromClient.getEmployeeId())) {
            if (deviceTokenExists(deviceTokenFromClient)) {
                logger.info("The token " + deviceTokenFromClient + " already exists");
                return Response.status(ClientResponse.Status.ACCEPTED).build();
            } else {
                DeviceToken newDeviceToken = new DeviceToken(employeeId, deviceTokenFromClient);

                factory = EntityManagerFactorySingleton.getSharedInstance();
                EntityManager em = factory.createEntityManager();
                try {
                    em.getTransaction().begin();
                    em.persist(newDeviceToken); // Add new entry in the database
                    em.getTransaction().commit(); 
                    logger.info("New device token for employee with ID " + employeeId);
                    return Response.status(ClientResponse.Status.CREATED).build();
                }
                finally {
                    em.close();
                }
            }

//        } else {
//            logger.info("No employee with ID " + deviceTokenFromClient.getEmployeeId());
//            return Response.status(ClientResponse.Status.NOT_FOUND).build();
//        }
        

    }
    
    /**
     * This function lists all transferRequests for the specified employee
     * the database.
     * @param employeeId the employee's id
     * @return a list of all deviceTokens for the specified employee
     */
    @GET
    @Path("{employeeId}/transferRequests")
    @Produces(Constants.JSON_MEDIA_TYPE_PRODUCE)
    public Response getEmployeeTransferRequests(@PathParam("employeeId") int employeeId) {
        TransferRequestResource transferRequestResource = new TransferRequestResource();
        List<TransferRequest> transferRequests = transferRequestResource.getTransferRequestsForEmployee(employeeId);
        List<TransferRequest> transferRequestsArrayList = new ArrayList<TransferRequest>(transferRequests);
        // if no device tokens exist for the employee
        if(transferRequestsArrayList.isEmpty()) {
            return Response.status(ClientResponse.Status.NO_CONTENT).build();
        }
        
        GenericEntity<List<TransferRequest>> entity = new GenericEntity<List<TransferRequest>>(transferRequestsArrayList) {};
        return Response.ok(entity).build();

    }


  /**
   * Get the employee with the username and password specified
   * @return The employee object if the combination is valid else null
   */
  public Employee getOneEmployeewithUsernameandPassword(String username, String password)
  {
    List<Employee> employeeList = getAllEmployeesFromDatabase();
    Employee theEmployee = new Employee();
    for (Employee employee : employeeList) {
      if ((employee.getUsername().equals(username)) && (employee.getPassword().equals(password))) {
        theEmployee = employee;
        logger.info("Employee with username: " + username + " was found.");
        return theEmployee;
      }
    }
    logger.info("No employee " + username + " with password " + password + " exists");
    return null;

  }

  /**
   * This function issues a query to the database to get all the employees
   */

  @SuppressWarnings("unchecked")
  public List<Employee> getAllEmployeesFromDatabase()
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      Query q = em.createQuery("select e from Employee e");
      return q.getResultList();
    }
    finally {
      em.close();
    }
  }

  /**
   * Check the database if there is an employee with the specified Id.
   * @param employeeId The id of the Employee
   * @return True if the Id exists otherwise False
   */

  public static boolean employeeIdExists(int employeeId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      EmployeeResource employeeResource = new EmployeeResource();
      List<Employee> employeeArrayList = new ArrayList<Employee>(employeeResource.getAllEmployeesFromDatabase());
      for(Employee e : employeeArrayList) {
          if(e.getEmployeeId() == employeeId) {
              return true;
          }
      }
      return false;
    }
    finally {
      em.close();
    }
  }

  /**
   * Get the Name of the Employee with the corresponding ID
   * @param employeeId The Id of the employee
   * @return The name of the employee with this Id
   */

  public static String getNameofEmployee(int employeeId)
  {
    EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
    EntityManager em = factory.createEntityManager();

    try {
      // Read the existing entries and write to console
      Query qName = em.createQuery("select e.name from Employee e WHERE e.employeeId = '" + employeeId + "'");
      Query qSurName = em.createQuery("select e.surname from Employee e WHERE e.employeeId = '" + employeeId + "'");

      return qName.getResultList().get(0) + " " + qSurName.getResultList().get(0);
    }
    finally {
      em.close();
    }
  }

}