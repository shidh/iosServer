package com.nttdata.bikes.restserver.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Ignore;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.entities.Employee;
import com.nttdata.bikes.entities.Pedelec;
import com.nttdata.bikes.entities.PedelecStation;
import com.nttdata.bikes.entities.Reservation;

@Ignore
public class TestObjects {
//	
//	public static List<Employee> listAllEmployees = new ArrayList<Employee>();
//	public static List<PedelecStation> listAllPedelecStations = new ArrayList<PedelecStation>();
//	public static List<Pedelec> listAllPedelecs = new ArrayList<Pedelec>();
//	public static List<Reservation> listAllReservations = new ArrayList<Reservation>();
//	
//	
//	public static void InitializeObjects() {
//		listAllEmployees.add(new Employee("Bob", "Brown", "user1", "pass1", "123", 0.0, 0.0));
//		listAllEmployees.add(new Employee("Alice", "Alles", "user2", "pass2", "12331", 0.1, 0.1));
//		listAllPedelecStations.add(new PedelecStation(0, "NTT Data", 12, 55.121345, 78.98745));
//		listAllPedelecStations.add(new PedelecStation(1, "BMW", 13, 98.56897, 12.4578));
//		listAllPedelecs.add(new Pedelec("California Ped", 85.4, true, false, true, listAllPedelecStations.get(0)));
//		listAllPedelecs.add(new Pedelec("Munich Ped", 12.48, false, true, false, listAllPedelecStations.get(1)));
//		listAllReservations.add(new Reservation(listAllPedelecs.get(0), listAllEmployees.get(0), new Date().getTime() - 2 * Constants.ONE_HOUR, 0, 123, 60.5, 10.24, 50));
//		listAllReservations.add(new Reservation(listAllPedelecs.get(1), listAllEmployees.get(1), new Date().getTime() - 3 * Constants.ONE_HOUR, 0, 321, 50.6, 20.54, 41));
//	}
//	
//	public static String ResponseToJson(Response response) {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(Feature.INDENT_OUTPUT,false);
//		mapper.configure(Feature.WRITE_ENUMS_USING_TO_STRING,true);
//		mapper.configure(Feature.USE_ANNOTATIONS, false);
//		mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
//		mapper.setSerializationInclusion(Inclusion.NON_NULL);
//		mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//		try {
//			String json = mapper.writeValueAsString(response.getEntity());
//			return json;
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
