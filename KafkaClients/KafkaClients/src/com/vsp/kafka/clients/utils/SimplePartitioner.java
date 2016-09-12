package com.vsp.kafka.clients.utils;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner 
{

	public SimplePartitioner( VerifiableProperties props ) 
	{
	}

	@Override
	public int partition(Object key, int nPartitions) 
	{
		int partition = 0;
		String partitionKey = (String)key;
		int di = partitionKey.lastIndexOf(' ');
		if( di > 0 )
		{
			partition = Integer.parseInt(partitionKey.substring( di + 1 ) ) % nPartitions;
		}
		
		return partition;
	}

}
