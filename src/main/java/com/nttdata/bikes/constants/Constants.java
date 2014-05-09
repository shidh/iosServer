package com.nttdata.bikes.constants;

/**
 * In this class we store all the constant variables that we use in the server
 * @author Isabel Max, Stefanos Stamogiorgos
 */

public class Constants {

	// File Paths
	public static String mockFilesBasePath = System.getProperty("user.dir") + "/webapps/ntt-bikes-server/mockfiles";
	
	// Database (Hibernate)
	public static final String PERSISTENCE_UNIT_NAME = "nttdata";
	
	// Jersey Umlauts
	public static final String JSON_MEDIA_TYPE_PRODUCE = "application/json; charset=utf-8";
	public static final String JSON_MEDIA_TYPE_CONSUME = "application/json";
	
	//URLS
	public static String URLLocal = "http://192.168.178.46:8080/ntt-bikes-server";
	public static String URLRemote = "http://192.168.178.46:8080/ntt-bikes-server";
	public static String IPLocalServerViaWIFI = "";
	
	public static String servicePathGetAllPedelecs = "rest/pedelec/getallpedelecs";
	public static String servicePathGetPedelecsFromStation = "rest/pedelec/getpedelecsfromstation";
	
	public static String servicePathGetAllPedelecStations = "rest/pedelecstation/getallstations";
	
	public static String servicePathGetAllEmployees = "rest/employee/getallemployees";
	
	public static String servicePathGetAllReservations = "rest/reservation/getallreservations";
	public static String servicePathgetCurrentReservationofPedelec = "rest/reservation/getcurrentreservationofpedelec";
	
	//Credentials
	public static String userNameLocal = "user";
	public static String pwLocal = "password";
	public static String userNameRemote = "user";
	public static String passwordRemote = "1nttios3pwbikes";
	
	//Date and Time
	public static long ONE_HOUR = 60 * 60 * 1000;
	
	//PushNotification
	public static String pushNotificationCertificate = System.getProperty("user.dir") + "/webapps/ntt-bikes-server/certificates/iOS13NTTBikesAPSCertificate_Production.p12";
	public static String pushNotificationPassword = "iOS13NTTBikes";
	public static boolean pushNotificationProduction = true;
}
