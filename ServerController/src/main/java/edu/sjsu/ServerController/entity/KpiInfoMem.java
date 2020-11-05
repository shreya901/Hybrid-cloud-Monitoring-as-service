package edu.sjsu.ServerController.entity;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import lombok.Data;

@Data
@Measurement(name = "Stats")
public class KpiInfoMem {

	

	@Column(name = "Mem_Percentage")
	private double percentage;
	
	@Column(name = "UTC")
	private long utc_time;
}