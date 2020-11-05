package edu.sjsu.VmAgent.dbConfig;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import edu.sjsu.VmAgent.Entity.ContainerInfo;
import lombok.extern.slf4j.Slf4j;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import edu.sjsu.VmAgent.Entity.Infos;

@Slf4j
public class DbUtilityKpi {

	

		static final String USER = "admin";
	    static final String PASS = "password";
	    static final String DB = "VMKPI";
	    private static InfluxDB Idb;
	 
	    public DbUtilityKpi() {
	    	DbUtilityKpi.Idb = InfluxDBFactory.connect("http://172.31.73.214:8086", USER, PASS);
//	    	Pong response = DbUtilityKpi.Idb.ping();
//	    	
//	    	if (response.getVersion().equalsIgnoreCase("unknown")) {
//	    		log.error("Error pinging server.");
//	    	    return;
//	    	} 
	    	DbUtilityKpi.Idb.enableBatch(100, 200, TimeUnit.MILLISECONDS);
	    	DbUtilityKpi.Idb.setRetentionPolicy("autogen");
	    	DbUtilityKpi.Idb.setDatabase(DB);
    	
	    }
	
	    public void insert(Infos info)
	    {
	   
	    	
	    	//System.out.println(info.toString());
	    	Point point = Point.measurement("VmStats")
	    			  .time(info.getUTC(),TimeUnit.MILLISECONDS)
	    			  .tag("FQDN", info.getFqdn())
	    			  .tag("Hostname",info.getHostname())
	    			  .addField("UTC", info.getUTC())
	    			  .addField("CPU_System", info.getCpu().getSys())
	    			  .addField("CPU_User", info.getCpu().getUser())
	    			  .addField("Memory_Used", info.getMem().getUsed())
	    			  .addField("Memory_Free", info.getMem().getFree())
	    			  .addField("Cpu_Percentage",info.getCpuPercentage())
	    			  .addField("Mem_Percentage", info.getMemoryPercentage())
	    			  
	    			  .build();

	    	DbUtilityKpi.Idb.write(point);
	    	
	    	DbUtilityKpi.Idb.close();
	    }


}
