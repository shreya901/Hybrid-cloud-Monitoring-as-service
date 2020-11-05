package edu.sjsu.VmAgent.dbConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import edu.sjsu.VmAgent.Entity.ContainerInfo;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;


import lombok.extern.slf4j.Slf4j;


	@Slf4j
	
	public class DbUtility {
		static final String USER = "admin";
	    static final String PASS = "password";
	    static final String DB = "PODCOUNT";
	    private static InfluxDB Idb;
	 
	    public DbUtility() {
	    	DbUtility.Idb = InfluxDBFactory.connect("http://172.31.73.214:8086", USER, PASS);
//	    	Pong response = DbUtility.Idb.ping();
//	    	
//	    	if (response.getVersion().equalsIgnoreCase("unknown")) {
//	    		log.error("Error pinging server.");
//	    	    return;
//	    	} 
    	
	 
	    }
	
	    public void insert(List<ContainerInfo> containerInfoList)
	    {
	   
	    	BatchPoints batchPoints = BatchPoints
	    			  .database(DB)
	    			  .retentionPolicy("autogen")
	    			  .build();
	    	
	    	for (ContainerInfo ci : containerInfoList)
	    	{
	    		Point point = Point.measurement("ContainerCount")
	    				.time(ci.getUTC(),TimeUnit.MILLISECONDS)
	    				.addField("ContainerType",ci.getContainerType())
	    				.addField("ContainerCount",ci.getContainerCount())
	    				.addField("UTC",ci.getUTC())
	    				.tag("FQDN",ci.getFqdn())
	    				.tag("HostName",ci.getHostname())
	    				.build();

	    		
		    	batchPoints.point(point);
	    	
	    		
	    	}

	    	
	    	DbUtility.Idb.write(batchPoints);
	    	DbUtility.Idb.disableBatch();
	    	DbUtility.Idb.close();
	    }

}
		



