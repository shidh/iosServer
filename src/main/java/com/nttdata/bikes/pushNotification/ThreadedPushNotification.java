package com.nttdata.bikes.pushNotification;

import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.nttdata.bikes.constants.Constants;
import com.nttdata.bikes.infrastructure.LoggingManager;

/**
 * This class sends the PushNotification in single threads, so that there is a problem with the Apple servers,
 * that this server has no problems in executing all requests.
 * @author Isabel Max
 *
 */

public class ThreadedPushNotification extends Thread {

	/**
	 * In a PushNotification you can send a message, which is displayed on the screen on the iPhone
	 * and a custom payload, which can be a kind of protocol between the App and the server.
	 * In this server we just use "test" as payload.
	 */
	private String message;
	private String payload;
	private List<String> deviceTokens;

	public ThreadedPushNotification(String message, String payload,
			List<String> deviceTokens) {
		this.message = message;
		this.payload = payload;
		this.deviceTokens = deviceTokens;
	}

	static  Logger logger = LoggingManager.getLogger();
	
	/**
	 * The thread starts and sends pushNotifications to all devices from the deviceTokenList
	 */
	public void run() {
		for (String deviceToken : this.deviceTokens){
			try {

				/* Build a blank payload to customize */
				PushNotificationPayload payload = PushNotificationPayload.complex();

				/* Customize the payload */
				payload.addAlert(this.message);
				payload.addBadge(1);
				payload.addSound("default");
				payload.addCustomDictionary("payload", this.payload);

				/* Push your custom payload */
				Push.payload(payload, Constants.pushNotificationCertificate,
						Constants.pushNotificationPassword,
						Constants.pushNotificationProduction, deviceToken);
				logger.info("No error while sending the pushnotification");
				
			} catch (CommunicationException e) {
				logger.error(e);
				e.printStackTrace();
			} catch (KeystoreException e) {
				logger.error(e);
				e.printStackTrace();
			} catch (JSONException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

}
