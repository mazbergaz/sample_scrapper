package org.mazb.samplescrapper.model;

import java.util.Date;

public class FlightSearchModel {
	
	private String origin;
	private String destination;
	private Date departureDate;
	private Date arrivalDate;
	private String tripType;
	private int passengerAdultNum;
	private int passengerChildrenNum;
	private int passengerInfantNum;
	private String currency;
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public int getPassengerAdultNum() {
		return passengerAdultNum;
	}
	public void setPassengerAdultNum(int passengerAdultNum) {
		this.passengerAdultNum = passengerAdultNum;
	}
	public int getPassengerChildrenNum() {
		return passengerChildrenNum;
	}
	public void setPassengerChildrenNum(int passengerChildrenNum) {
		this.passengerChildrenNum = passengerChildrenNum;
	}
	public int getPassengerInfantNum() {
		return passengerInfantNum;
	}
	public void setPassengerInfantNum(int passengerInfantNum) {
		this.passengerInfantNum = passengerInfantNum;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Override
	public String toString() {
		return "FlightSearchModel [origin=" + origin + ", destination="
				+ destination + ", departureDate=" + departureDate
				+ ", arrivalDate=" + arrivalDate + ", tripType=" + tripType
				+ ", passengerAdultNum=" + passengerAdultNum
				+ ", passengerChildrenNum=" + passengerChildrenNum
				+ ", passengerInfantNum=" + passengerInfantNum + ", currency="
				+ currency + "]";
	}
	
}
