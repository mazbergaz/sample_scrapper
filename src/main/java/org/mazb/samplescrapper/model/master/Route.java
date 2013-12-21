package org.mazb.samplescrapper.model.master;

import java.util.List;

public class Route {
	
	private String stationCode;
	private List<Country> routes;
	private List<String> currencies;
	private String baseCurrency;
	
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public List<Country> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Country> routes) {
		this.routes = routes;
	}
	public List<String> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	
}
