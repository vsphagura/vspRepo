package com.vsp.storm.commitsample;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import com.vsp.storm.commitsample.CommitFeedListener;
import com.vsp.storm.commitsample.EmailCounter;
import com.vsp.storm.commitsample.EmailExtractor;

public class LocalTopologyRunner 
{
  private static final int TEN_MINUTES = 600000;

  public static void main(String[] args) 
  {
	  LocalCluster cluster = null;
	  
	  try
	  {
		    TopologyBuilder builder = new TopologyBuilder();
		
		    builder.setSpout("commit-feed-listener", new CommitFeedListener());
		
		    builder
		        .setBolt("email-extractor", new EmailExtractor())
		        .shuffleGrouping("commit-feed-listener");
		
		    builder
		        .setBolt("email-counter", new EmailCounter())
		        .fieldsGrouping("email-extractor", new Fields("email"));
		
		    Config config = new Config();
		    config.setDebug(false);
		
		    StormTopology topology = builder.createTopology();
		
		    cluster = new LocalCluster();
		    cluster.submitTopology("commit-count-topology",
		        config,
		        topology);
		
		    Utils.sleep(TEN_MINUTES);
		    cluster.killTopology("commit-count-topology");
		    cluster.shutdown();
	  }
	  catch( Exception ex )
	  {
		  System.out.println(ex.getMessage());
		  ex.printStackTrace();
	  }
	  finally
	  {
		  if( cluster != null )
		  {
			  cluster.killTopology("commit-count-topology");
			  cluster.shutdown();
		  }
	  }
  }
}
