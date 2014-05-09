package com.nttdata.bikes.restserver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * This class is used when "/jerseytest" is appended to the REST URL of the server.
 * @author Isabel Max
 */


@Path("/jerseytest")
public class JerseyTest {

	@GET
	@Produces("text/html")
	public String sayHello(@Context HttpServletRequest servletRequest) {
		return "Hello Bamboo!";
	}

}
