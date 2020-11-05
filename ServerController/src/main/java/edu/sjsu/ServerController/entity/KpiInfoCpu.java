package edu.sjsu.ServerController.entity;




import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import lombok.Data;


@Data
@Measurement(name = "Stats")
public class KpiInfoCpu {

	
	
	@Column(name = "Cpu_Percentage")
	private double percentage;

	

	
	@Column(name = "UTC")
	private long utc_time;
	
	
	
	
	

	


    
}
