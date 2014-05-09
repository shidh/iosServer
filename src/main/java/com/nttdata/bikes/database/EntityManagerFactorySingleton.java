package com.nttdata.bikes.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.nttdata.bikes.constants.Constants;

/**
 * This class is used, when you want to open a database connection.
 * If you open a new EntityManager every time, the server crashes every 3 minutes.
 * @author Isabel Max
 *
 */
public class EntityManagerFactorySingleton {

	private static EntityManagerFactory sharedInstance;
	
	public EntityManagerFactorySingleton() { }
	
	/**
	 * In this method you get the Singleton.
	 * @return
	 */
	public static EntityManagerFactory getSharedInstance(){
		if ((sharedInstance == null) || (!(sharedInstance.isOpen()))) sharedInstance = Persistence
				.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME);
		return sharedInstance;
	}
	
}
