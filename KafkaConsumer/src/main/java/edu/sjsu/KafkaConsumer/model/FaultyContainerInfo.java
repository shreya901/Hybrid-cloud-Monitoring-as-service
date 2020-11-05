package edu.sjsu.KafkaConsumer.model;


import lombok.Data;

@Data
public class FaultyContainerInfo {
	 private String fqdn;
	 private double UTC;

}