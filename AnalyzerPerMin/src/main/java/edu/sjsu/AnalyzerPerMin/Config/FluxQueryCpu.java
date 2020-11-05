package edu.sjsu.AnalyzerPerMin.Config;
import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import com.influxdb.query.*;

import edu.sjsu.AnalyzerPerMin.NoDataException.NotEnoughData;

import lombok.extern.slf4j.Slf4j;

import com.influxdb.client.flux.FluxClient;
import com.influxdb.client.flux.FluxClientFactory;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;

@Slf4j
public class FluxQueryCpu extends DbConnections {


	  static  Instant start;
	  static Instant stop;
	  static int tries=0;
       
    public static void setRange() {
   	    log.debug("Inside setRange");
    	 
   	 	String query1 = "from (bucket:\"KPIDB-LOW/autogen\") |> range(start: -100d) |> filter(fn: (r) => r._measurement == \"CpuPerMinTopicLowLoad\" and r._field == \"UTC\") |>  last()";
   	 	List<FluxTable> tables1 = fluxClient.query(query1);
   	 	if (tables1.isEmpty()==true){
   	 		String query2 = "from (bucket:\"KPIDB-LOW/autogen\") |> range(start: -100d) |> filter(fn: (r) => r._measurement == \"Stats\" and r._field == \"UTC\") |>  first()";
   	 		List<FluxTable> tables2 = fluxClient.query(query2);
   	 		
   	 	    start=DbConnections.getStartTime(tables2);
   	 		stop=Instant.ofEpochMilli(start.toEpochMilli()+ hour);
   	 		}
    	
   	 	else {
   	 		
   	 		
   	 		start=DbConnections.getStartTime(tables1);
	 		stop=Instant.ofEpochMilli(start.toEpochMilli()+ hour);
   	 	}
    }
	
	    public static void performQuery() throws NotEnoughData, InterruptedException {

	    	

	    	 String query = "from (bucket:\"KPIDB-LOW/autogen\") |> range(start: "+ start.toString()+ " , stop: " + stop.toString()+ ") |> filter(fn: (r) => r._measurement == \"Stats\" and r._field == \"Cpu_Percentage\") |> group(columns: [\"FQDN\"], mode:\"by\")|> window(every: 1m) |> mean()|> duplicate(column: \"_stop\", as: \"_time\") |> yield()";
	    	 List<FluxTable> tables = fluxClient.query(query);

	    	 
	    	 while(tables.size()<30 && tries<3)
	    	 {
	    		 Thread.sleep(10000);
	    		 tables = fluxClient.query(query);
	    		 tries+=1;
	    	 }
	    	 
	    	 if (tables.size()<30 && tries>=3)
	    	 {
	    		 FluxQueryCpu.Idb_cpu.close();
	    		 fluxClient.close();
	    		 log.debug("Exception!!!!!!");
	    		 throw new NotEnoughData("Not Enough Cpu Usage Points to calculate mean");
	    		 
	    	 }
	    	 
	    	 else {
	    		 
	    	 
	    	 for (FluxTable fluxTable : tables) {
	             List<FluxRecord> records = fluxTable.getRecords();
	             for (FluxRecord fluxRecord : records) {
//	            	 System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
	            	 Point p = Point.measurement("CpuPerMinTopicLowLoad")
	            			 .time(fluxRecord.getTime().toEpochMilli(),TimeUnit.MILLISECONDS)
	            			 .tag("FQDN",(String) fluxRecord.getValueByKey("FQDN"))
							.addField("AvgCpuUsagePerMin", Double.parseDouble(fluxRecord.getValueByKey("_value").toString()))
							.addField("UTC",fluxRecord.getTime().toEpochMilli())
							.build();
	           
	            	 
	            	 FluxQueryCpu.Idb_cpu.write(p);

	             }
	         }
	    	 start=stop;
	    	 stop=Instant.ofEpochMilli(stop.toEpochMilli()+ hour);
	    	 }
	    
	    }

}
