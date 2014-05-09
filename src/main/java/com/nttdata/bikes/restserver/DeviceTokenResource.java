package com.nttdata.bikes.restserver;
import com.nttdata.bikes.database.EntityManagerFactorySingleton;
import com.nttdata.bikes.entities.DeviceToken;
import com.nttdata.bikes.infrastructure.LoggingManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.Path;
import org.apache.log4j.Logger;

/**
 * This class is used when "/devicetoken" is appended to the REST URL of the
 * server.
 *
 * @author Stefanos Stamogiorgos
 */
@Path("deviceTokens")
public class DeviceTokenResource {

    private static Logger logger = LoggingManager.getLogger();
    static EntityManagerFactory factory;
    


    /**
     * Checks whether a deviceToken already exists in the database
     *
     * @param deviceToken The deviceToken we want to check
     * @return true if it exists otherwise false
     */
    public static boolean deviceTokenExists(String deviceToken) {
        EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
        EntityManager em = factory.createEntityManager();

        try {
            // Read the existing entries and write to console
            Query q = em.createQuery("select e from DeviceToken e WHERE e.tokenString = '" + deviceToken + "'");

            return ((q.getResultList() != null && !q.getResultList().isEmpty()));
        } finally {
            em.close();
        }
    }

    /**
     * Get the DeviceTokens for a specific employee from the database.
     *
     * @param employeeId The Id of the corresponding employee
     * @return A List with the device tokens. The list might me empty if there
     * are not tokens
     */
    @SuppressWarnings("unchecked")
    public static List<DeviceToken> getDeviceTokensforEmployee(int employeeId) {
        EntityManagerFactory factory = EntityManagerFactorySingleton.getSharedInstance();
        EntityManager em = factory.createEntityManager();

        try {
            // Read the existing entries and write to console
            Query q = em.createQuery("select e from DeviceToken e WHERE e.employeeId = '" + employeeId + "'");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
