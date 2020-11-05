package edu.sjsu.ServerController.entity;

import lombok.Data;


public class ApiInfoPod {
	private String dbName;
	private String podName;
	private String metricType;
	public String getDbName() {
		  return dbName;
		 }
	public String getMetricType() {
		  return metricType;
		 }
	public String getPodName() {
		  return podName;
		 }
	public void setDbName(String d) {
		this.dbName=d;
	}
	public void setPodName(String p) {
		this.podName=p;
	}
	public void setMetricType(String m) {
		this.metricType=m;
	}
}
