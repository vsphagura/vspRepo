package com.vsp.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;

public class DbConnector 
{
	//Query
    static String CREATE_DEMO = "CREATE KEYSPACE demo WITH replication "
       + "= {'class':'SimpleStrategy', 'replication_factor':3};";
    //Cluster
    Cluster cluster = null;
    Session session = null;
    
    public DbConnector( String ksName, String ip )
    {
	    //creating Cluster object
	    cluster = Cluster.builder().addContactPoint(ip).build();
	    //Creating Session object
	    session = cluster.connect();
    }
    
	public void createNUseKeyspace( String name )
	{
		try 
		{
			//Executing the query
			 session.execute( CREATE_DEMO );
			//using the KeySpace
			 session.execute("USE demo");
			 
			 System.out.println("Keyspace created");
		} 
		catch (AlreadyExistsException e) 
		{
			System.out.println("Keyspace already exists!");
		}
	}
	
	public static void main( String[] args )
	{
		DbConnector db = new DbConnector("demo", "192.168.10.215");
		db.createNUseKeyspace("demo");
	}
}
