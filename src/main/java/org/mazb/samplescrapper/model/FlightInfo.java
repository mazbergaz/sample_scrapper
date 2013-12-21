package org.mazb.samplescrapper.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightInfo {
	
	private String tripType;
	private Date goDate;
	private Date returnDate;
	private List<FlightDetail> goFlightDetails;
	private List<FlightDetail> returnFlightDetails;
	
	private String goDateStr;
	private String returnDateStr;
	
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

	public Date getGoDate() {
		return goDate;
	}

	public void setGoDate(Date goDate) {
		this.goDate = goDate;
		this.goDateStr = new SimpleDateFormat("MM/dd/yyyy").format(goDate);
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
		this.returnDateStr = new SimpleDateFormat("MM/dd/yyyy").format(returnDate);
	}

	public String getGoDateStr() {
		return goDateStr;
	}

	public String getReturnDateStr() {
		return returnDateStr;
	}

	@Override
	public String toString() {
		return "FlightInfo [tripType=" + tripType + ", goDate=" + goDate
				+ ", returnDate=" + returnDate + ", goFlightDetails="
				+ goFlightDetails + ", returnFlightDetails="
				+ returnFlightDetails + "]";
	}
	
}
