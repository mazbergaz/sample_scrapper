package org.mazb.samplescrapper.model;

public class FareDetail{
	private String adultPriceString;
	private String childrenPriceString;
	private String infantPriceString;

	public String getAdultPriceString() {
		return adultPriceString;
	}

	public void setAdultPriceString(String adultPriceString) {
		this.adultPriceString = adultPriceString;
	}

	public String getChildrenPriceString() {
		return childrenPriceString;
	}

	public void setChildrenPriceString(String childrenPriceString) {
		this.childrenPriceString = childrenPriceString;
	}

	public String getInfantPriceString() {
		return infantPriceString;
	}

	public void setInfantPriceString(String infantPriceString) {
		this.infantPriceString = infantPriceString;
	}

	@Override
	public String toString() {
		return "FareDetail [adultPriceString=" + adultPriceString
				+ ", childrenPriceString=" + childrenPriceString
				+ ", infantPriceString=" + infantPriceString + "]";
	}
	
}
