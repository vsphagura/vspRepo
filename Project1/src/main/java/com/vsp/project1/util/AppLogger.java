package com.vsp.project1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

	private static Logger logger = null;

	public AppLogger() 
	{
	}

	public AppLogger( Class clz )
	{
		logger = LoggerFactory.getLogger(clz);
	}
	
	public void info( String str )
	{
		logger.info(str); 
	}
	
	public void debug( String str )
	{
		logger.debug(str); 
	}
	
	public void warn( String str )
	{
		logger.warn(str); 
	}
	
	public void error( String str )
	{
		logger.error(str); 
	}
	
}
