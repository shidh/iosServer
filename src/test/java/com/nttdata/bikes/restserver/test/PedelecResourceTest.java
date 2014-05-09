/**
 * 
 */
package com.nttdata.bikes.restserver.test;

import static org.junit.Assert.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;


import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.entities.Pedelec;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;


public class PedelecResourceTest {
	
//	WebResource service;
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
//	 * Test method for {@link com.nttdata.bikes.restserver.PedelecResource#getAllPedelecs()}.
//	 */
//	@Test
//	public void testGetAllPedelecs() {
//	    String answerFromServer = service.path(Constants.servicePathGetAllPedelecs).accept(MediaType.APPLICATION_JSON).get(String.class);
//	    
//	    ObjectMapper mapper = new ObjectMapper();
////	    mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//	    
//	    try {
//	    	
//	    	JSONObject jobj = new JSONObject(answerFromServer);
//	    	String jsonString = jobj.getString("pedelec");
//			List<Pedelec> pedelecList = mapper.readValue(jsonString, new TypeReference<List<Pedelec>>(){});
//			
//			assertNotNull(pedelecList);
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
//		
//	}
//	
//	/**
//	 * Test method for {@link com.nttdata.bikes.restserver.PedelecResource#getPedelecsFromStationwithId(int Stationid)}.
//	 */
//	
//	@Test
//	public void testGetPedelecsFromStationwithId_1() {
//	    String answerFromServer = service.path(Constants.servicePathGetPedelecsFromStation).queryParam("stationId","1").accept(MediaType.APPLICATION_JSON).get(String.class);
//	    
//	    ObjectMapper mapper = new ObjectMapper();
////	    mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//	    
//	    try {
//	    	
//	    	JSONObject jobj = new JSONObject(answerFromServer);
//	    	String jsonString = jobj.getString("pedelec");
//			List<Pedelec> pedelecList = mapper.readValue(jsonString, new TypeReference<List<Pedelec>>(){});
//			
//			assertNotNull(pedelecList);
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
//		
//	}
//	
//	
//	
//	private static URI getBaseURI() {
//	    return UriBuilder.fromUri(Constants.URLRemote).build();
//	 }
//	
//	
	
	

}
