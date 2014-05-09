package com.nttdata.bikes.restserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.nttdata.bikes.pushNotification.PushNotification;

/**
 * This class is used in order to test a simple PushNotification on your device.
 * Just enter the following URL (in any browser): http://ios13nttdatabruegge.in.tum.de:8080/nttdata/rest/pushnotification/sendtodevice?deviceToken=<deviceTokenWhichYougetFromTheAppOnYourDevice> 
 * @author Isabel Max
 *
 */

@Path("pushnotifications")
public class PushNotificationTest {
	
	/**
	 * This method sends a simple "Hello World" PushNotification to make sure, that every certificate is installed correctly.
	 * @param deviceToken
	 * @return
	 */
	
	@GET
	@Path("sendtodevice")
	public Response requestReservationOfColleague(
		@QueryParam(value = "deviceToken") String deviceToken) {
		PushNotification.sendWithDeviceToken("Hello World!", "test", deviceToken);
		return Response.status(200).build();
	}
}
