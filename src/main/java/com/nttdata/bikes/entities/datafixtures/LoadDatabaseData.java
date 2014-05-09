package com.nttdata.bikes.entities.datafixtures;

import java.io.PrintWriter;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.codec.digest.DigestUtils;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.entities.Employee;
import com.nttdata.bikes.entities.Pedelec;
import com.nttdata.bikes.entities.PedelecStation;
import com.nttdata.bikes.entities.Reservation;

public class LoadDatabaseData {
	/**
	 * This function fills in the database
	 *
	 * @author Isabel Max
	 */
	public void loadEntityData(EntityManager em, PrintWriter output) {
		// Create new Employees
		Employee emp1 = new Employee("Frank", "vonEitzen", "nttdata",
				DigestUtils.md5Hex("pass1"), null, 0, 0);
		persist(em, output, emp1);

		Employee emp2 = new Employee("Michaela", "Gluchow", "gluchowm",
				DigestUtils.md5Hex("pass2"), "+491776442021", 0, 0);
		persist(em, output, emp2);

		Employee emp3 = new Employee("Matthias", "Schwab", "schwabma",
				DigestUtils.md5Hex("pass3"), "+4915123013458", 0, 0);
		persist(em, output, emp3);

		Employee emp4 = new Employee("Nikolay", "Terziyski", "furious_gorilla",
				DigestUtils.md5Hex("pass4"), "+4917684427079", 0, 0);
		persist(em, output, emp4);

		Employee emp5 = new Employee("Altug", "Bayram", "altugbay",
				DigestUtils.md5Hex("pass5"), "+4917692924487", 0, 0);
		persist(em, output, emp5);

		Employee emp6 = new Employee("Isabel", "Max", "isa..91",
				DigestUtils.md5Hex("pass6"), "+491773424498", 0, 0);
		persist(em, output, emp6);

		Employee emp7 = new Employee("Cagla", "Sahinli", "cagla.sahinli",
				DigestUtils.md5Hex("pass7"), "+4915739040406", 0, 0);
		persist(em, output, emp7);

		Employee emp8 = new Employee("Valentin", "Schlattinger", "schlatti",
				DigestUtils.md5Hex("pass8"), "+491719938909", 0, 0);
		persist(em, output, emp8);

		Employee emp9 = new Employee("Stefanos", "Stamogiorgos", "stefanostam",
				DigestUtils.md5Hex("pass9"), "+4917684427078", 0, 0);
		persist(em, output, emp9);

		Employee emp10 = new Employee("Felix", "Belau", "king_belau",
				DigestUtils.md5Hex("pass10"), "+491705262147", 0, 0);
		persist(em, output, emp10);

		// Create new Pedelec Stations
		PedelecStation pedStation1 = new PedelecStation("NTT Data", 10,
				48.184129, 11.587649);
		persist(em, output, pedStation1);

		PedelecStation pedStation2 = new PedelecStation("BMW", 5, 48.17689,
				11.560128);
		persist(em, output, pedStation2);

		PedelecStation pedStation3 = new PedelecStation("FMI-HS1", 3,
				48.262466, 11.669164);
		persist(em, output, pedStation3);

		// Create new Pedelecs and use the previously created stations as the
		// last argument
		Pedelec ped1 = new Pedelec("New York Pedelec", 100.0, true, true, true,
				pedStation1);
		persist(em, output, ped1);

		Pedelec ped2 = new Pedelec("Washington Pedelec", 75.0, true, true,
				false, pedStation1);
		persist(em, output, ped2);

		Pedelec ped3 = new Pedelec("Philadelphia Pedelec", 50.0, true, true,
				false, pedStation1);
		persist(em, output, ped3);

		Pedelec ped4 = new Pedelec("Athens Pedelec", 50.0, true, true, false,
				pedStation1);
		persist(em, output, ped4);

		Pedelec ped5 = new Pedelec("Munich Pedelec", 30.0, true, true, false,
				pedStation3);
		persist(em, output, ped5);

		Pedelec ped6 = new Pedelec("London Pedelec", 10.0, true, true, false,
				pedStation1);
		persist(em, output, ped6);

		Pedelec ped7 = new Pedelec("Barcelona Pedelec", 15.0, true, true,
				false, pedStation1);
		persist(em, output, ped7);

		Pedelec ped8 = new Pedelec("Madrid Pedelec", 20.0, true, true, false,
				pedStation1);
		persist(em, output, ped8);

		Pedelec ped9 = new Pedelec("Berlin Pedelec", 25.0, true, true, false,
				pedStation2);
		persist(em, output, ped9);

		Pedelec ped10 = new Pedelec("Paris Pedelec", 30.0, true, true, false,
				pedStation2);
		persist(em, output, ped10);

		Pedelec ped11 = new Pedelec("Rome Pedelec", 35.0, true, true, false,
				pedStation2);
		persist(em, output, ped11);

		Pedelec ped12 = new Pedelec("Milano Pedelec", 45.0, true, true, false,
				pedStation2);
		persist(em, output, ped12);

		Pedelec ped13 = new Pedelec("California Pedelec", 95.0, true, true,
				false, pedStation2);
		persist(em, output, ped13);

		Pedelec ped14 = new Pedelec("Garching Pedelec", 90.0, true, true,
				false, pedStation2);
		persist(em, output, ped14);

		Pedelec ped15 = new Pedelec("Dresden Pedelec", 80.0, true, true, false,
				pedStation2);
		persist(em, output, ped15);

		Pedelec ped16 = new Pedelec("Pittsburgh Pedelec", 70.0, true, true,
				false, pedStation3);
		persist(em, output, ped16);

		Pedelec ped17 = new Pedelec("Stuttgart Pedelec", 65.0, true, true,
				false, pedStation2);
		persist(em, output, ped17);

		Pedelec ped18 = new Pedelec("Sofia Pedelec", 55.0, true, true, false,
				pedStation2);
		persist(em, output, ped18);

		Pedelec ped19 = new Pedelec("Kempten Pedelec", 50.0, true, true, false,
				pedStation1);
		persist(em, output, ped19);

		Pedelec ped20 = new Pedelec("Instanbul Pedelec", 45.0, true, true,
				false, pedStation2);
		persist(em, output, ped20);

		Pedelec ped21 = new Pedelec("Cuba Pedelec", 100.0, true, true, false,
				pedStation1);
		persist(em, output, ped21);

		/*
		 * Create six sample Reservations and use the previously created
		 * pedelecs and stations. The reservations and the information in the
		 * pedelecs table is inconsistent at the moment.
		 */

		Reservation res1 = new Reservation(ped1, emp7, new Date().getTime() - 3
				* Constants.ONE_HOUR,
				((new Date()).getTime() - Constants.ONE_HOUR), 1.0, 2.0, 3.0,
				4.0);
		persist(em, output, res1);

		Reservation res2 = new Reservation(ped2, emp2, new Date().getTime() - 2
				* Constants.ONE_HOUR, 0, 4.0, 3.0, 2.0, 1.0);
		persist(em, output, res2);

		Reservation res3 = new Reservation(ped3, emp3, new Date().getTime(), 0,
				1.0, 2.0, 3.0, 4.0);
		persist(em, output, res3);

		Reservation res4 = new Reservation(ped4, emp4, new Date().getTime() - 2
				* Constants.ONE_HOUR, 0, 4.0, 3.0, 2.0, 1.0);
		persist(em, output, res4);

		@SuppressWarnings("deprecation")
		Reservation res5 = new Reservation(ped7, emp5, new Date().getTime(),
				new Date(2013, 10, 10).getTime(), 1.0, 2.0, 3.0, 4.0);
		persist(em, output, res5);

		Reservation res6 = new Reservation(ped6, emp1,
				(new Date().getTime() - 2 * Constants.ONE_HOUR), 0, 4.0, 3.0,
				2.0, 1.0);
		persist(em, output, res6);
	}

	private void persist(EntityManager em, PrintWriter output, Object object) {
		boolean success = false;
		try {
			em.getTransaction().begin();
			em.persist(object);
			if (output != null) {
				output.print(object.toString() + " created");
			}
			em.getTransaction().commit();
			success = true;
		} finally {
			if (!success) {
				if (output != null) {
					output.print(object.toString() + " failed");
				}
			}
		}
	}

}
