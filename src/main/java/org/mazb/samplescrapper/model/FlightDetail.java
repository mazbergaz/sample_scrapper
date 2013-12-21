package org.mazb.samplescrapper.model;

import java.util.Date;

public class FlightDetail {
	
	private String flightCode;
	private Date date;
	private String departureStation;
	private String arrivalStation;
	private String departureTime;
	private String arrivalTime;
	private FareDetail hiFare;
	private FareDetail lowFare;
	
	public FlightDetail(){
		
	}
	
	public String getFlightCode() {
		return flightCode;
	}
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public FareDetail getHiFare() {
		return hiFare;
	}
	public void setHiFare(FareDetail hiFare) {
		this.hiFare = hiFare;
	}
	public FareDetail getLowFare() {
		return lowFare;
	}
	public void setLowFare(FareDetail lowFare) {
		this.lowFare = lowFare;
	}
	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	@Override
	public String toString() {
		return "FlightDetail [flightCode=" + flightCode + ", date=" + date
				+ ", departureStation=" + departureStation
				+ ", arrivalStation=" + arrivalStation + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + ", hiFare="
				+ hiFare + ", lowFare=" + lowFare + "]";
	}
	
	
}
