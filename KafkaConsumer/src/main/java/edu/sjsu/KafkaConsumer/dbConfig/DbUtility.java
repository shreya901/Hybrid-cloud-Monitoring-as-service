package edu.sjsu.KafkaConsumer.dbConfig;

import java.util.concurrent.TimeUnit;



import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;

import edu.sjsu.KafkaConsumer.model.FaultyContainerInfo;
import edu.sjsu.KafkaConsumer.model.Infos;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class DbUtility {
	
	
	static final String USER = "admin";
    static final String PASS = "password";
    static final String DB = "KPIDB-MED";
    private static InfluxDB Idb;

    public DbUtility() {
    	DbUtility.Idb = InfluxDBFactory.connect("http://172.31.73.214:8086", USER, PASS);

    	DbUtility.Idb.enableBatch(100, 200, TimeUnit.MILLISECONDS);
    	DbUtility.Idb.setRetentionPolicy("autogen");
    	DbUtility.Idb.setDatabase(DB);
    	
    	
//    	Pong response = this.Idb.ping();
//    	if (response.getVersion().equalsIgnoreCase("unknown")) {
//    		log.error("Error pinging server.");
//    	    return;
//    	} 
    	
    }
    
    public void insert(Infos info)
    {
    	//info.getUptime(),TimeUnit.MILLISECONDS
    	Point point = Point.measurement("Stats")
    			  .time(info.getUTC(),TimeUnit.MILLISECONDS)
    			  .addField("LoadType", info.getTopicName())
    			  .tag("Hostname", info.getHostname())
    			  .tag("FQDN", info.getFqdn())
    			  .addField("UTC", info.getUTC())
    			  .addField("CPU_System", info.getCpu().getSys())
    			  .addField("CPU_User", info.getCpu().getUser())
    			  .addField("Memory_Used", info.getMem().getUsed())
    			  .addField("Memory_Free", info.getMem().getFree())
    			  .addField("Cpu_Percentage",info.getCpuPercentage())
    			  .addField("Mem_Percentage", info.getMemoryPercentage())
    			  
    			  .build();
//    	BatchPoints batchPoints = BatchPoints
//    			  .database(DB)
//    			  .retentionPolicy("autogen")
//    			  .build();
//    	batchPoints.point(point);
//    	DbUtility.Idb.disableBatch();
    	DbUtility.Idb.write(point);
    	DbUtility.Idb.close();
    	
    }
    public void insertFC(FaultyContainerInfo fc)
    {
    	Point point = Point.measurement("FaultyContainer")
    		 .time(fc.getUTC(),TimeUnit.MILLISECONDS)
  			  .tag("FQDN", fc.getFqdn())
  			  .build();
    			
    	DbUtility.Idb.write(point);
    }

}
