package org.mazb.samplescrapper.model.master;

import java.util.List;

public class Country {
	
	private String countryCode;
	private boolean isIslandTransfer;
	private List<String> stations;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public boolean isIslandTransfer() {
		return isIslandTransfer;
	}
	public void setIslandTransfer(boolean isIslandTransfer) {
		this.isIslandTransfer = isIslandTransfer;
	}
	public List<String> getStations() {
		return stations;
	}
	public void setStations(List<String> stations) {
		this.stations = stations;
	}
	
}
