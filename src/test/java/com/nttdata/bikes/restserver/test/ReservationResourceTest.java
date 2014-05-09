/**
 * 
 */
package com.nttdata.bikes.restserver.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.entities.Reservation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * @author Isabel Max
 *
 */
public class ReservationResourceTest {

//	
//WebResource service;
//	
//	@Before
//	public void setUp() {
//		ClientConfig config = new DefaultClientConfig();
//	    Client client = Client.create(config);
//	    client.addFilter(new HTTPBasicAuthFilter(Constants.userNameRemote,Constants.passwordRemote));
//	    service = client.resource(getBaseURI());
//	}
//	
//	/**
//	 * Test method for {@link com.nttdata.bikes.restserver.ReservationResource#getAllReservations()}.
//	 */
//	@Test
//	public void testGetAllReservations() {
//		String answerFromServer = service.path(Constants.servicePathGetAllReservations).accept(MediaType.APPLICATION_JSON).get(String.class);
//	    
//	    ObjectMapper mapper = new ObjectMapper();
////	    mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//	    
//	    try {
//	    	
//	    	JSONObject jobj = new JSONObject(answerFromServer);
//	    	String jsonString = jobj.getString("reservation");
//			List<Reservation> reservationList = mapper.readValue(jsonString, new TypeReference<List<Reservation>>(){});
//			
//			assertNotNull(reservationList);
//			
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Test method for {@link com.nttdata.bikes.restserver.ReservationResource#getCurrentReservationOfPedelec(int)}.
//	 */
//	@Test
//	public void testGetCurrentReservationOfPedelec() {
//		String answerFromServer = service.path(Constants.servicePathGetAllReservations).queryParam("pedelecId","3").accept(MediaType.APPLICATION_JSON).get(String.class);
//	    
//	    ObjectMapper mapper = new ObjectMapper();
////	    mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//	    
//	    try {
//	    	
//	    	JSONObject jobj = new JSONObject(answerFromServer);
//	    	String jsonString = jobj.getString("reservation");
//			List<Reservation> reservationList = mapper.readValue(jsonString, new TypeReference<List<Reservation>>(){});
//			
//			assertEquals(reservationList.size(), 1);
//			
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Test method for {@link com.nttdata.bikes.restserver.ReservationResource#postAndUpdateReservations(java.util.List)}.
//	 */
////	@Test
////	public void testPostAndUpdateReservations() {
////		ReservationResource reservationResource = new ReservationResource();
////		//this test only works, if getAllReservationsFromDatabase() works!
////		List<Reservation> allReservations = reservationResource.getAllReservationsFromDatabase();
////		int countReservationsBefore = allReservations.size();
////		
////		//this test only works, if getPedelecsAtStation() works, and if there is a pedelec at the station
////		PedelecResource pedelecResource = new PedelecResource();
////		Pedelec pedelec = pedelecResource.getPedelecsAtStation(1).get(0);
////		
////		//TODO: delete created reservation!!
////		Reservation reservation = new Reservation(pedelec, null,  ((new Date()).getTime() - 2 * Constants.ONE_HOUR), 0, 4.0, 3.0, 2.0, 1.0);
////		List<Reservation> reservationList = new ArrayList<Reservation>();
////		reservationList.add(reservation);
////		
////		reservationResource.postAndUpdateReservations(reservationList);
////		
////		assertTrue("Creation didn't work!", reservationResource.getAllReservationsFromDatabase().size() == countReservationsBefore + 1);
////	}
////
////	/**
////	 * Test method for {@link com.nttdata.bikes.restserver.ReservationResource#getCurrentReservation(int)}.
////	 */
////	@Test
////	public void testGetCurrentReservation() {
////		fail("Not yet implemented");
////	}
//	
//	private static URI getBaseURI() {
//	    return UriBuilder.fromUri(Constants.URLRemote).build();
//	 }

}
