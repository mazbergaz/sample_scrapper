package org.mazb.samplescrapper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mazb.samplescrapper.model.FareDetail;
import org.mazb.samplescrapper.model.FlightDetail;
import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.springframework.stereotype.Service;

@Service
public class AirasiaWebConverter {
	
	/**
	 * parse html response
	 * @param responseDoc
	 * @param model
	 * @return
	 */
	public FlightInfo getFlightInfo(Document responseDoc, FlightSearchAirAsiaModel model){
		FlightInfo flightInfo = new FlightInfo(model.getMarketStructure());
		try {
			flightInfo.setGoDate(new SimpleDateFormat("MM/dd/yyyy").parse(model.getDatePicker1()));
			flightInfo.setReturnDate(new SimpleDateFormat("MM/dd/yyyy").parse(model.getDatePicker2()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Elements els = responseDoc.select("div.availabilityInputContent");
		for (int availabilityIndex = 0; availabilityIndex < els.size(); availabilityIndex++) {
			Element el = els.get(availabilityIndex);
			// there are only 2 div.availabilityInputContent (0=go(depart) and 1=return)
			List<FlightDetail> flightDetails = availabilityIndex == 0 ? flightInfo.getGoFlightDetails() : flightInfo.getReturnFlightDetails();
			Elements tables = el.select("table.rgMasterTable");
			if (tables != null && tables.size() > 0) {
				Element table = tables.first(); // there's only 1 rgMasterTable in a div.availabilityInputContent
				Elements rgRows = tables.select("tr.rgRow");
				if (rgRows != null && rgRows.size() > 0) {
					// each row represents a flightDetail
					for (Element rgRow : rgRows) {
						flightDetails.add(getFlightDetail(availabilityIndex, rgRow, model));
					}
				}
			}
		}
		return flightInfo;
	}
	
	/**
	 * parse row to retrieve FlightDetail
	 * @param typeIndex
	 * @param rgRow
	 * @param model
	 * @return
	 */
	private FlightDetail getFlightDetail(int typeIndex, Element rgRow, FlightSearchAirAsiaModel model){
		FlightDetail flightDetail = new FlightDetail();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
			date = typeIndex==0 ? sdf.parse(model.getDatePicker1()) : sdf.parse(model.getDatePicker2());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		flightDetail.setDate(date);
		
		// departure & arrival time & station
		Element segmentStation = rgRow.select("div.segmentStation").first();
		Elements ps = segmentStation.children();
		String[] departureTimeStation = getTimeAndStation(ps.get(0).ownText());
		flightDetail.setDepartureTime(departureTimeStation[0]);
		flightDetail.setDepartureStation(departureTimeStation[1]);
		String[] arrivalTimeStation = getTimeAndStation(ps.get(1).ownText());
		flightDetail.setArrivalTime(arrivalTimeStation[0]);
		flightDetail.setArrivalStation(arrivalTimeStation[1]);
		
		// flight code and fareDetail
		Elements tds = rgRow.select("td[class^=resultFare]");
		if(tds!=null && tds.size()>0){
			//there are 2 td, 0=lowfare & 1=hiflyer
			Element td1 = tds.get(0);
			
			// flight code
			flightDetail.setFlightCode(getFlightCode(td1));
				
			// fare detail
			flightDetail.setLowFare(getFareDetail(td1));
			flightDetail.setHiFare(getFareDetail(tds.get(1)));
		}
		//System.out.println(flightDetail);
		return flightDetail;
	}
	
	/**
	 * parse cell to retrieve flight code
	 * @param td
	 * @return
	 */
	private String getFlightCode(Element td){
		String result = null;
		Element fareRadio = td.getElementById("fareRadio");
		if(fareRadio!=null){
			String radioVal = fareRadio.child(0).val();
			result = radioVal.substring(radioVal.indexOf("|")+1, radioVal.indexOf("|")+8).replace("~", " ");
		}
		return result;
	}
	
	/**
	 * parse cell to retrieve fare details
	 * @param td
	 * @return
	 */
	private FareDetail getFareDetail(Element td){
		FareDetail fareDetail = new FareDetail();
		Elements divPrices = td.select("div.price");
		
		// fare detail adult and kid
		Elements divPaxTypeDisplay = td.select("div.paxTypeDisplay");
		if(divPaxTypeDisplay!=null && divPaxTypeDisplay.size()>0){
			for(int l=0; l<divPaxTypeDisplay.size(); l++){
				String type = divPaxTypeDisplay.get(l).ownText();
				String priceVal = divPrices.get(l).select("span").first().ownText();
				if("Adult".equals(type)){
					fareDetail.setAdultPriceString(priceVal);
				}else if("Kid".equals(type)){
					fareDetail.setChildrenPriceString(priceVal);
				}
			}
		}
		
		Elements divBolds = td.select("div.bold");
		if(divBolds!=null && divBolds.size()>0){
			String type = divBolds.first().ownText();
			String priceVal = divPrices.get(divPrices.size()-1).select("span").first().ownText();
			if("Infant".equals(type)){
				fareDetail.setInfantPriceString(priceVal);
			}
		}
		
		return fareDetail;
	}
	
	private String[] getTimeAndStation(String text){
		String[] result = new String[2];
		StringTokenizer strToken = new StringTokenizer(text, " ()");
		int i = 0;
		while(strToken.hasMoreTokens()){
			String token = strToken.nextToken();
			if(i==0){
				result[0] = token.substring(0, 2)+":"+token.substring(2);
			}else{
				result[1] = token;
			}
			i++;
		}
		return result;
	}
	
}
