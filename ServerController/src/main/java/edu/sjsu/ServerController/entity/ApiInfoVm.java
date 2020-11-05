package edu.sjsu.ServerController.entity;

public class ApiInfoVm {
	private String dbName;
	private String vmName;
	private String metricType;
	public String getDbName() {
		  return dbName;
		 }
	public String getMetricType() {
		  return metricType;
		 }
	public String getVmName() {
		  return vmName;
		 }
	public void setDbName(String d) {
		this.dbName=d;
	}
	public void setVmName(String v) {
		this.vmName=v;
	}
	public void setMetricType(String m) {
		this.metricType=m;
	}

}
