package org.mazb.samplescrapper.model;

public class AirasiaFlightDetail {

	private String type;
	private String flight_type;
	private String dmy;
	private long depart_time;
	private long arrive_time;
	private long total;
	private long fare_price;
	private long taxes_and_fees;
	private long infant_fare_price;
	private String sellkey;
	private String flight_number;
	private long details_index;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlight_type() {
		return flight_type;
	}

	public void setFlight_type(String flight_type) {
		this.flight_type = flight_type;
	}

	public String getDmy() {
		return dmy;
	}

	public void setDmy(String dmy) {
		this.dmy = dmy;
	}

	public long getDepart_time() {
		return depart_time;
	}

	public void setDepart_time(long depart_time) {
		this.depart_time = depart_time;
	}

	public long getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(long arrive_time) {
		this.arrive_time = arrive_time;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getFare_price() {
		return fare_price;
	}

	public void setFare_price(long fare_price) {
		this.fare_price = fare_price;
	}

	public long getTaxes_and_fees() {
		return taxes_and_fees;
	}

	public void setTaxes_and_fees(long taxes_and_fees) {
		this.taxes_and_fees = taxes_and_fees;
	}

	public long getInfant_fare_price() {
		return infant_fare_price;
	}

	public void setInfant_fare_price(long infant_fare_price) {
		this.infant_fare_price = infant_fare_price;
	}

	public String getSellkey() {
		return sellkey;
	}

	public void setSellkey(String sellkey) {
		this.sellkey = sellkey;
	}

	public String getFlight_number() {
		return flight_number;
	}

	public void setFlight_number(String flight_number) {
		this.flight_number = flight_number;
	}

	public long getDetails_index() {
		return details_index;
	}

	public void setDetails_index(long details_index) {
		this.details_index = details_index;
	}

	@Override
	public String toString() {
		return "AirasiaFlightDetail [type=" + type + ", flight_type="
				+ flight_type + ", dmy=" + dmy + ", depart_time=" + depart_time
				+ ", arrive_time=" + arrive_time + ", total=" + total
				+ ", fare_price=" + fare_price + ", taxes_and_fees="
				+ taxes_and_fees + ", infant_fare_price=" + infant_fare_price
				+ ", sellkey=" + sellkey + ", flight_number=" + flight_number
				+ ", details_index=" + details_index + "]";
	}

}
