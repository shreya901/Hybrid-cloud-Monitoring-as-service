package edu.sjsu.VmAnalyzer.Config;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.influxdb.client.flux.FluxClient;
import com.influxdb.client.flux.FluxClientFactory;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public abstract class DbConnections {
	static final String USER = "admin";
    static final String PASS = "password";
    static final String DB = "VMKPI";
    static final long hour= 3600000l ;
    public static InfluxDB Idb_cpu;
    public static InfluxDB Idb_memory;
    protected static final FluxClient fluxClient =FluxClientFactory.create("http://127.0.0.1:8086");;

    public static void DbUtility() {
    	DbConnections.Idb_cpu = InfluxDBFactory.connect("http://127.0.0.1:8086", USER, PASS);
    	DbConnections.Idb_memory = InfluxDBFactory.connect("http://127.0.0.1:8086", USER, PASS);
//    	Pong response = DbConnections.Idb.ping();
//    	if (response.getVersion().equalsIgnoreCase("unknown")) {
//    		log.error("Error pinging server.");
//    	    return;
//    	} 
    	
    	DbConnections.Idb_cpu.enableBatch(100, 2000, TimeUnit.MILLISECONDS);
    	DbConnections.Idb_cpu.setRetentionPolicy("autogen");
    	DbConnections.Idb_cpu.setDatabase(DB);
    	DbConnections.Idb_memory.enableBatch(100, 2000, TimeUnit.MILLISECONDS);
    	DbConnections.Idb_memory.setRetentionPolicy("autogen");
    	DbConnections.Idb_memory.setDatabase(DB);
    

    	
    }
    
    public static Instant getStartTime(List<FluxTable> tables)
    {
    	long start_epoch=Long.MAX_VALUE; 
    	Instant start = null;
    	for (FluxTable fluxTable : tables) {
    		List<FluxRecord> records = fluxTable.getRecords();
    		for (FluxRecord fluxRecord : records) {
//    			System.out.println(fluxRecord.getTime().toEpochMilli());
    			if (fluxRecord.getTime().toEpochMilli() < start_epoch)
    			{
    				
    				start_epoch=fluxRecord.getTime().toEpochMilli() ;
    				start = fluxRecord.getTime();
    			}
    		}
    		
    		
    	}
		return start;
    	
    }

}
