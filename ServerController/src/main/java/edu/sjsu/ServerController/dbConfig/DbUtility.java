package edu.sjsu.ServerController.dbConfig;

import java.util.ArrayList;
import java.util.List;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;


import edu.sjsu.ServerController.entity.KpiInfoCpu;
import edu.sjsu.ServerController.entity.KpiInfoMem;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DbUtility {
	static final String USER = "admin";
    static final String PASS = "password";
    static final String DBHIGH = "KPIDBHIGH";
    static final String DBMED = "KPIDB-MED";
    static final String DBLOW = "KPIDB-LOW";
    static final String DBVM = "VMKPI";
    private static InfluxDB Idb;

 //172.31.73.214
    public DbUtility() {
    	DbUtility.Idb = InfluxDBFactory.connect("http://127.0.0.1:8086", USER, PASS);

//    	Pong response = this.Idb.ping();
//    	
//    	if (response.getVersion().equalsIgnoreCase("unknown")) {
//    		log.error("Error pinging server.");
//    	    return;
//    	} 
//    	
    }
    public <T> List<T> Query(String db,String hostName, String metricType) {

    	String val=null;
    	QueryResult queryResult = null;
    	
    	if (metricType.equals("cpu")) {
    		val="Cpu_Percentage";
    		switch (db)
    		{
    			case "high":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBHIGH));
    			case "med":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBMED));
    			case "low":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBLOW));
    			case "vm":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBVM));
    				InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
                	List<KpiInfoCpu> KpiInfosList =  new ArrayList<KpiInfoCpu>();
            		KpiInfosList = resultMapper.toPOJO(queryResult, KpiInfoCpu.class);
            		return (List<T>) KpiInfosList;
    				
    		}

    	}else {
    		val="Mem_Percentage";
    		switch (db)
    		{
    			case "high":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBHIGH));
    			case "med":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBMED));
    			case "low":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBLOW));
    			case "vm":
    				queryResult = DbUtility.Idb.query(new Query("Select "+val+" ,UTC from \"Stats\" WHERE \"Hostname\"='"+hostName+"'", DBVM));
    				InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
                	List<KpiInfoMem> KpiInfosList =  new ArrayList<KpiInfoMem>();
            		KpiInfosList = resultMapper.toPOJO(queryResult, KpiInfoMem.class);
            		return (List<T>) KpiInfosList;
    				
    		}
    		
    	}
		return null;

    }

}
