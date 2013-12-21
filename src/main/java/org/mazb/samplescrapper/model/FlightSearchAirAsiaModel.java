package org.mazb.samplescrapper.model;

public class FlightSearchAirAsiaModel {
	
	private String origin;
	private String destination;
	private String marketStructure;
	private String currency = "default";
	private String marketDay1;
	private String marketMonth1;
	private String marketDay2;
	private String marketMonth2;
	private String adultPassengerNum;
	private String childrenPassengerNum;
	private String infantPassengerNum;
	private String buttonSubmit;
	private String datePicker1;
	private String datePicker2;
	
	public String getDatePicker1() {
		return datePicker1;
	}
	public void setDatePicker1(String datePicker1) {
		this.datePicker1 = datePicker1;
	}
	public String getDatePicker2() {
		return datePicker2;
	}
	public void setDatePicker2(String datePicker2) {
		this.datePicker2 = datePicker2;
	}
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
	public String getMarketStructure() {
		return marketStructure;
	}
	public void setMarketStructure(String marketStructure) {
		this.marketStructure = marketStructure;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMarketDay1() {
		return marketDay1;
	}
	public void setMarketDay1(String marketDay1) {
		this.marketDay1 = marketDay1;
	}
	public String getMarketMonth1() {
		return marketMonth1;
	}
	public void setMarketMonth1(String marketMonth1) {
		this.marketMonth1 = marketMonth1;
	}
	public String getMarketDay2() {
		return marketDay2;
	}
	public void setMarketDay2(String marketDay2) {
		this.marketDay2 = marketDay2;
	}
	public String getMarketMonth2() {
		return marketMonth2;
	}
	public void setMarketMonth2(String marketMonth2) {
		this.marketMonth2 = marketMonth2;
	}
	public String getAdultPassengerNum() {
		return adultPassengerNum;
	}
	public void setAdultPassengerNum(String adultPassengerNum) {
		this.adultPassengerNum = adultPassengerNum;
	}
	public String getChildrenPassengerNum() {
		return childrenPassengerNum;
	}
	public void setChildrenPassengerNum(String childrenPassengerNum) {
		this.childrenPassengerNum = childrenPassengerNum;
	}
	public String getInfantPassengerNum() {
		return infantPassengerNum;
	}
	public void setInfantPassengerNum(String infantPassengerNum) {
		this.infantPassengerNum = infantPassengerNum;
	}
	public String getButtonSubmit() {
		return buttonSubmit;
	}
	public void setButtonSubmit(String buttonSubmit) {
		this.buttonSubmit = buttonSubmit;
	}
	
	@Override
	public String toString() {
		return "FlightSearchAirAsiaModel [origin=" + origin + ", destination="
				+ destination + ", marketStructure=" + marketStructure
				+ ", currency=" + currency + ", marketDay1=" + marketDay1
				+ ", marketMonth1=" + marketMonth1 + ", marketDay2="
				+ marketDay2 + ", marketMonth2=" + marketMonth2
				+ ", adultPassengerNum=" + adultPassengerNum
				+ ", childrenPassengerNum=" + childrenPassengerNum
				+ ", infantPassengerNum=" + infantPassengerNum
				+ ", buttonSubmit=" + buttonSubmit + "]";
	}
	
}
