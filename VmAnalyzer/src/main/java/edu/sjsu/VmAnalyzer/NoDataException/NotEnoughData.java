package edu.sjsu.VmAnalyzer.NoDataException;

public class NotEnoughData extends Exception {
	String err;
	public NotEnoughData(String str) {
		err=str;
	   }
	   public String toString(){ 
	    return ("NotEnoughData Occurred: "+err) ;
	   }
}