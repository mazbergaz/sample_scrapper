package org.mazb.samplescrapper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mazb.samplescrapper.common.CommonHelper;
import org.mazb.samplescrapper.model.AirasiaFlightDetail;
import org.mazb.samplescrapper.model.FareDetail;
import org.mazb.samplescrapper.model.FlightDetail;
import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class AirasiaMobileConverter {
	
	private final String PART_DEPART = "depart";
	private final String PART_RETURN = "return";
	private final String FARE_PART_LOWFARE = "list-low-fare";
	private final String FARE_PART_HIFLYER = "list-hi-flyer";
	private final Gson gson = new Gson();
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private final SimpleDateFormat sdfParse = new SimpleDateFormat("yyyyMMdd");
	
	public FlightInfo getFlightInfo(Document responseDoc, FlightSearchAirAsiaModel model){
		Date godate = null;
		Date returndate = null;
		try {
			godate = (model.getDatePicker1()!=null && model.getDatePicker1().length()>0) ? sdf.parse(model.getDatePicker1()) : null;
			returndate = (model.getDatePicker2()!=null && model.getDatePicker2().length()>0) ? sdf.parse(model.getDatePicker2()) : null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		FlightInfo flightInfo = new FlightInfo(model.getMarketStructure());
		flightInfo.setGoDate(godate);
		flightInfo.setReturnDate(returndate);
		
		String[] parts = {PART_DEPART, PART_RETURN};
		for(String part : parts){
			String partDate = null;
			if(PART_DEPART.equals(part)){
				partDate = (godate==null) ? null : sdfParse.format(godate);
				setDetails(responseDoc, model, flightInfo.getGoFlightDetails(), part, partDate);//flightInfo.getGoFlightDetails();
			}else{
				partDate = (returndate==null) ? null : sdfParse.format(returndate);
				setDetails(responseDoc, model, flightInfo.getReturnFlightDetails(), part, partDate);//flightInfo.getReturnFlightDetails();
			}
		}
		
		return flightInfo;
	}
	
	private void setDetails(Document responseDoc, FlightSearchAirAsiaModel model, List<FlightDetail> flightDetails, String part, String partDate){
		Element div = partDate==null ? null : responseDoc.getElementById("flight-"+part+"-"+partDate);
		if(div!=null){
			String[] fareParts ={FARE_PART_LOWFARE, FARE_PART_HIFLYER};
			for(String farePart : fareParts){
				Element divlistlowfare = div.getElementById(part+"-"+farePart);
				if(divlistlowfare!=null){
					Element ul = divlistlowfare.select("ul.flightsList").first();
					Elements lis = ul.children();
					if(lis!=null && lis.size()>0){
						for(Element li : lis){
							FlightDetail flightDetail = getFlightDetail(flightDetails, model.getCurrency(), farePart, li);
							if(PART_DEPART.equals(part)){
								flightDetail.setDepartureStation(model.getOrigin());
								flightDetail.setArrivalStation(model.getDestination());
							}else{
								flightDetail.setDepartureStation(model.getDestination());
								flightDetail.setArrivalStation(model.getOrigin());
							}
						}
					}
				}
			}
		}
	}
	
	private FlightDetail getFlightDetail(List<FlightDetail> list, String currency, String farePart, Element li){
		FlightDetail flightDetail = null;
		Element divFare = li.select("div.fare").first();
		String datafare = divFare.attr("data-fare");
		if(datafare!=null && datafare.length()>0){
			AirasiaFlightDetail airasiaDetail = gson.fromJson(datafare, AirasiaFlightDetail.class);
			String flightCode = airasiaDetail.getFlight_number();
			flightDetail = getFlightDetailIfAlreadyAdded(list, flightCode);
			if(flightDetail==null){
				flightDetail = new FlightDetail();
				flightDetail.setFlightCode(flightCode);
				try {
					flightDetail.setDate(sdfParse.parse(airasiaDetail.getDmy()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String sellkey = airasiaDetail.getSellkey();
				flightDetail.setDepartureTime(sellkey.substring(sellkey.indexOf(":")-2, sellkey.indexOf(":")+3));
				flightDetail.setArrivalTime(sellkey.substring(sellkey.lastIndexOf(":")-2, sellkey.lastIndexOf(":")+3));
				flightDetail.setFlightCode(airasiaDetail.getFlight_number());
				list.add(flightDetail);
			}
			FareDetail fareDetail = new FareDetail();
			fareDetail.setAdultPriceString(CommonHelper.getFormattedCurrency(currency, airasiaDetail.getFare_price()));
			fareDetail.setInfantPriceString(CommonHelper.getFormattedCurrency(currency, airasiaDetail.getInfant_fare_price()));
			if(FARE_PART_LOWFARE.equals(farePart)){
				flightDetail.setLowFare(fareDetail);
			}else{
				flightDetail.setHiFare(fareDetail);
			}
		}
		return flightDetail;
	}
	
	private FlightDetail getFlightDetailIfAlreadyAdded(List<FlightDetail> list, String flightCode){
		FlightDetail flightDetail = null;
		int i=0;
		while(i<list.size()){
			if(list.get(i).getFlightCode().equals(flightCode)){
				flightDetail = list.get(i);
				break;
			}
			i++;
		}
		return flightDetail;
	}
	
}
