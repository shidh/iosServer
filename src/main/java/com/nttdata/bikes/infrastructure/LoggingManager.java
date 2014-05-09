package com.nttdata.bikes.infrastructure;

import org.apache.log4j.*;

/**
 *	This is the class used for logging.
 *@author Stefanos Stamogiorgos
 */

public final class LoggingManager {

	private static Logger singletonLogger;

	private LoggingManager(){

	}

	public static Logger getLogger(){
		if (singletonLogger == null){
			singletonLogger = CreateLogger();
		}

		return singletonLogger;
	}

	private static Logger CreateLogger()
	{
		Logger logger = Logger.getRootLogger();
		PropertyConfigurator.configure("config/log4j.properties");	// Create logger from Config file, this file must be created to the server.
		return logger;
	}

	public static void SetLoggerLevel(String newLevel)
	{
		Level level = Level.toLevel(newLevel);
		singletonLogger.setLevel(level);
	}
}