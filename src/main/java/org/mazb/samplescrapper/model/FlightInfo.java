package org.mazb.samplescrapper.model;

import java.util.ArrayList;
import java.util.List;

public class FlightInfo {
	
	private String tripType;
	private List<FlightDetail> goFlightDetails;
	private List<FlightDetail> returnFlightDetails;
	
	public FlightInfo(){
		this.goFlightDetails = new ArrayList<FlightDetail>();
		this.returnFlightDetails = new ArrayList<FlightDetail>();
	}
	
	public FlightInfo(String tripType){
		this.tripType = tripType;
		this.goFlightDetails = new ArrayList<FlightDetail>();
		this.returnFlightDetails = new ArrayList<FlightDetail>();
	}
	
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public void addGoFlightDetail(FlightDetail flightDetail){
		goFlightDetails.add(flightDetail);
	}
	public void addReturnFlightDetail(FlightDetail flightDetail){
		goFlightDetails.add(flightDetail);
	}
	public List<FlightDetail> getGoFlightDetails() {
		return goFlightDetails;
	}
	public void setGoFlightDetails(List<FlightDetail> goFlightDetails) {
		this.goFlightDetails = goFlightDetails;
	}
	public List<FlightDetail> getReturnFlightDetails() {
		return returnFlightDetails;
	}
	public void setReturnFlightDetails(List<FlightDetail> returnFlightDetails) {
		this.returnFlightDetails = returnFlightDetails;
	}

	@Override
	public String toString() {
		return "FlightInfo [\n\ttripType=" + tripType + ", \n\tgoFlightDetails="
				+ goFlightDetails + ", \n\treturnFlightDetails="
				+ returnFlightDetails + "\n]";
	}
	
}
