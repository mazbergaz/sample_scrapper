package org.mazb.samplescrapper.client;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mazb.samplescrapper.common.Constants;
import org.mazb.samplescrapper.model.FareDetail;
import org.mazb.samplescrapper.model.FlightDetail;
import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.springframework.stereotype.Service;

@Service
public class AirAsiaHttpClient {
	
	/**
	 * parse model to request mode, printToLog.
	 * retrieve html from file, parse to flightInfo
	 * @param model
	 * @return
	 */
	public FlightInfo postToAirAsiaMockup(FlightSearchAirAsiaModel model){
		FlightInfo result = null;
		
		File input = new File("/Users/bimo/Documents/nostratech/cv/wego/test02.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			result = getFlightInfo(doc, model);
			System.out.println("\n"+result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public FlightInfo postToAirAsia(FlightSearchAirAsiaModel model){
		FlightInfo result = null;
		try {
			/*Document docpost = Jsoup.connect("http://booking.airasia.com/Search.aspx")
			.cookies(populateCookies())
			.referrer("http://booking11.airasia.com/Page/SkySalesSelect.aspx")
			.userAgent("Mozilla/5.0")
			.data(populateRequestData(model))
			.post();*/
	
			Connection.Response res = Jsoup.connect("http://booking.airasia.com/Select.aspx")
					.cookies(populateCookies())
					.referrer("http://booking11.airasia.com/Page/SkySalesSelect.aspx")
					.userAgent("Mozilla/5.0")
					.data(populateRequestData(model))
			        .method(Method.POST)
					.execute();
			
			Document docget = Jsoup.connect("http://booking.airasia.com/Select.aspx")
					.cookies(populateCookies())
					.referrer("http://booking11.airasia.com/Page/SkySalesSelect.aspx")
					.userAgent("Mozilla/5.0")
					.get();
			
			result = getFlightInfo(docget, model);
			System.out.println("\n"+result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * parse html response
	 * @param responseDoc
	 * @param model
	 * @return
	 */
	private FlightInfo getFlightInfo(Document responseDoc, FlightSearchAirAsiaModel model){
		FlightInfo flightInfo = null;
		Elements els = responseDoc.select("div.availabilityInputContent");
		if(els!=null){
			flightInfo = new FlightInfo(model.getMarketStructure());
			for(int availabilityIndex=0; availabilityIndex<els.size() ; availabilityIndex++){
				Element el = els.get(availabilityIndex);
				// there are only 2 div.availabilityInputContent (0=go(depart) and 1=return)
				List<FlightDetail> flightDetails = availabilityIndex==0 ? flightInfo.getGoFlightDetails() : flightInfo.getReturnFlightDetails();
				Elements tables = el.select("table.rgMasterTable");
				if(tables!=null && tables.size()>0){
					Element table = tables.first(); //there's only 1 rgMasterTable in a div.availabilityInputContent
					Elements rgRows = tables.select("tr.rgRow");
					if(rgRows!=null && rgRows.size()>0){
						// each row represents a flightDetail
						for(Element rgRow : rgRows){
							flightDetails.add(getFlightDetail(availabilityIndex, rgRow, model));
						}
					}
				}
				if(availabilityIndex==0){
					if(flightInfo.getGoFlightDetails().size()>0){
						flightInfo.setGoDate(flightInfo.getGoFlightDetails().get(0).getDate());
					}
				}else{
					if(flightInfo.getReturnFlightDetails().size()>0){
						flightInfo.setReturnDate(flightInfo.getReturnFlightDetails().get(0).getDate());
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
		System.out.println(flightDetail);
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
	
	public static void main(String[] args) {
		AirAsiaHttpClient client = new AirAsiaHttpClient();
		FlightSearchAirAsiaModel model = new FlightSearchAirAsiaModel();
		model.setAdultPassengerNum("3");
		model.setButtonSubmit("Search");
		model.setChildrenPassengerNum("2");
		model.setCurrency("default");
		model.setDestination("DPS");
		model.setInfantPassengerNum("1");
		model.setMarketDay1("30");
		model.setMarketDay2("31");
		model.setMarketMonth1("2014-01");
		model.setMarketMonth2("2014-01");
		model.setDatePicker1("01/30/2014");
		model.setDatePicker2("01/31/2014");
		model.setMarketStructure(Constants.TripType.ROUNDTRIP);
		model.setOrigin("CGK");
		
		client.postToAirAsiaMockup(model);
		
		/*try {
			Document docget = Jsoup.connect("http://booking.airasia.com/Select.aspx")
					.userAgent("Mozilla/5.0")
					.get();
			Element el = docget.getElementById("allInFareArrowContainter");
			System.out.println(el.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
	
	/**
	 * prepare all request data
	 * @param model
	 * @return
	 */
	private Map<String, String> populateRequestData(FlightSearchAirAsiaModel model){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24RadioButtonMarketStructure", model.getMarketStructure());
		map.put("ControlGroupCompactView_AvailabilitySearchInputCompactVieworiginStation1", model.getOrigin());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24TextBoxMarketOrigin1", model.getOrigin());
		map.put("ControlGroupCompactView_AvailabilitySearchInputCompactViewdestinationStation1", model.getDestination());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24TextBoxMarketDestination1", model.getDestination());
		map.put("ControlGroupCompactView%24MCCControlCompactSearchView%24DropDownListCurrencies", "");
		map.put("ControlGroupCompactView%24MultiCurrencyConversionViewCompactSearchView%24DropDownListCurrency", "default");
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListMarketDay1", model.getMarketDay1());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListMarketMonth1", model.getMarketMonth1());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListMarketDay2", model.getMarketDay2());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListMarketMonth2", model.getMarketMonth2());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListPassengerType_ADT", ""+model.getAdultPassengerNum());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListPassengerType_CHD", ""+model.getChildrenPassengerNum());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListPassengerType_INFANT", ""+model.getInfantPassengerNum());
		map.put("__EVENTTARGET", "");
		map.put("__EVENTARGUMENT", "");
		map.put("pageToken", "");
		map.put("culture", "id-ID");
		map.put("promocode", "");
		map.put("ControlGroupCompactView%24ButtonSubmit", model.getButtonSubmit());
		map.put("ControlGroupCompactView%24AvailabilitySearchInputCompactView%24DropDownListSearchBy", "columnView");
		map.put("__VIEWSTATE", "wEPDwUBMGRktapVDbdzjtpmxtfJuRZPDMU9XYk=");
		map.put("date_picker", model.getDatePicker1());
		map.put("date_picker", model.getDatePicker2());
		return map;
	}
	
	/**
	 * provide common cookies for request
	 * @return
	 */
	private Map<String, String> populateCookies(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("optimizelySegments", "%7B%22192696121%22%3A%22false%22%2C%22192747026%22%3A%22referral%22%2C%22192747027%22%3A%22ff%22%2C%22192734033%22%3A%22none%22%2C%22196875818%22%3A%22ff%22%2C%22198133615%22%3A%22referral%22%2C%22198389126%22%3A%22none%22%2C%22197393471%22%3A%22false%22%7D"); 
		map.put("optimizelyEndUserId", "oeu1371471357917r0.8329969913544446"); 
		map.put("optimizelyBuckets", "%7B%7D"); 
		map.put("__utma", "203916751.1251250523.1371471358.1387313045.1387380487.7"); 
		map.put("__utmz", "203916751.1387380487.7.3.utmcsr=airasia.com|utmccn=(referral)|utmcmd=referral|utmcct=/"); 
		map.put("s_rsid", "aa-airasia-id-prd"); 
		map.put("s_vi", "[CS]v1|28DF7EFF852A1A19-40000106C0004106[CE]"); 
		map.put("true_loc", "id"); 
		map.put("skysales", "1125835274.20480.0000"); 
		map.put("AKSB", "s=1387274017867&r=http%3A//booking.airasia.com/Select.aspx"); 
		map.put("__utmc", "203916751"); 
		map.put("s_sess", " s_cc=true; s_sq=aa-airasia-id-prd%2Caa-airasia-global%3D%2526pid%253Dwww.airasia.com%25253Aid%25253Aid%25253Ahome.page%2526pidt%253D1%2526oid%253Dfunctiononclick(event)%25257BreturnucSearchForm.buttonSearchNow_Click()%25253B%25257D%2526oidt%253D2%2526ot%253DSUBMIT;"); 
		map.put("LanguageSelect", "id/id"); 
		map.put("ScheSTO", "true"); 
		map.put("__utmb", "203916751.2.10.1387380487");
		map.put("ASP.NET_SessionId", "emdbm0ytc4pyezuyxijgsk45");
		map.put("aakau", "1387433575~id=8f0094b82bd1dc9cb46ba8aa452a7c65");
		map.put("UserSessionExpired", "1");
		return map;
	}
	
}
