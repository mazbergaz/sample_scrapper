package org.mazb.samplescrapper.client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mazb.samplescrapper.common.CommonHelper;
import org.mazb.samplescrapper.common.Constants;
import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.mazb.samplescrapper.util.AirasiaMobileConverter;
import org.mazb.samplescrapper.util.AirasiaWebConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class AirAsiaHttpClient {
	
	private AirasiaWebConverter airasiaWebConverter;
	private AirasiaMobileConverter airasiaMobileConverter;

	/**
	 * just for testing
	 * parse model to request mode, printToLog.
	 * retrieve html from file, parse to flightInfo
	 * @param model
	 * @return
	 */
	public FlightInfo postToAirAsiaWebMockup(FlightSearchAirAsiaModel model, File input){
		FlightInfo result = null;
		
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			result = airasiaWebConverter.getFlightInfo(doc, model);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public FlightInfo postToAirAsiaWeb(FlightSearchAirAsiaModel model){
		FlightInfo result = null;
		try {
	
			Connection.Response res = Jsoup.connect("http://booking.airasia.com/Compact.aspx")
					.cookies(populateCookies())
					.referrer("http://booking11.airasia.com/Page/SkySalesSelect.aspx")
					.userAgent("Mozilla/5.0")
					.header("Connection", "keep-alive")
					.header("Host", "booking.airasia.com")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.data(populateRequestData(model))
			        .method(Method.POST)
					.execute();
			
			System.out.println(res.body());
			CommonHelper.writeToFile("/Users/bimo/Documents/test/res.txt", res.body());
			
			Document docget = Jsoup.connect("http://booking.airasia.com/Select.aspx")
					.cookies(populateCookies())
					.referrer("http://booking11.airasia.com/Page/SkySalesSelect.aspx")
					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:26.0) Gecko/20100101 Firefox/26.0")
					.header("Connection", "keep-alive")
					.header("Host", "booking.airasia.com")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.get();
			
			System.out.println(docget.body());
			CommonHelper.writeToFile("/Users/bimo/Documents/test/docget.txt", docget.body().html());
			
			result = airasiaWebConverter.getFlightInfo(docget, model);
			System.out.println("\n"+result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * just for testing
	 * parse model to request mode, printToLog.
	 * retrieve html from file, parse to flightInfo
	 * @param model
	 * @return
	 */
	public FlightInfo postToAirasiaMobileMockup(FlightSearchAirAsiaModel model, File input){
		FlightInfo result = null;
		
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			result = airasiaMobileConverter.getFlightInfo(doc, model);
			//System.out.println("\n"+result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public FlightInfo postToAirasiaMobile(FlightSearchAirAsiaModel model){
		FlightInfo result = null;
		try {
	
			Document res = Jsoup.connect("https://mobile.airasia.com/id/search")
					.cookies(populateCookiesMobile(model.getCurrency()))
					.referrer("https://mobile.airasia.com/id/")
					.userAgent("Mozilla/5.0")
					.header("Connection", "keep-alive")
					.header("Host", "mobile.airasia.com")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.data(populateRequestDataMobile(model))
					.timeout(60*1000)
			        .post();
			
			//System.out.println(res.body());
			result = airasiaMobileConverter.getFlightInfo(res, model);
			
			//System.out.println("\n"+result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		AirAsiaHttpClient client = new AirAsiaHttpClient();
		FlightSearchAirAsiaModel model = new FlightSearchAirAsiaModel();
		model.setAdultPassengerNum("3");
//		model.setButtonSubmit("Search");
//		model.setChildrenPassengerNum("2");
		model.setCurrency("default");
		model.setDestination("DPS");
		model.setInfantPassengerNum("1");
		model.setMarketDay1("30");
		model.setMarketDay2("31");
		model.setMarketMonth1("2013-12");
		model.setMarketMonth2("2013-12");
		model.setDatePicker1("12/30/2013");
		model.setDatePicker2("12/31/2013");
		model.setMarketStructure(Constants.TripType.M_ROUNDTRIP);
		model.setOrigin("CGK");
		
		client.postToAirasiaMobile(model);
		
	}
	
	/**
	 * prepare all request data
	 * @param model
	 * @return
	 */
	private Map<String, String> populateRequestData(FlightSearchAirAsiaModel model){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$RadioButtonMarketStructure", model.getMarketStructure());
		map.put("ControlGroupCompactView_AvailabilitySearchInputCompactVieworiginStation1", model.getOrigin());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$TextBoxMarketOrigin1", model.getOrigin());
		map.put("ControlGroupCompactView_AvailabilitySearchInputCompactViewdestinationStation1", model.getDestination());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$TextBoxMarketDestination1", model.getDestination());
		map.put("ControlGroupCompactView$MCCControlCompactSearchView$DropDownListCurrencies", "");
		map.put("ControlGroupCompactView$MultiCurrencyConversionViewCompactSearchView$DropDownListCurrency", "default");
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListMarketDay1", model.getMarketDay1());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListMarketMonth1", model.getMarketMonth1());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListMarketDay2", model.getMarketDay2());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListMarketMonth2", model.getMarketMonth2());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListPassengerType_ADT", ""+model.getAdultPassengerNum());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListPassengerType_CHD", ""+model.getChildrenPassengerNum());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListPassengerType_INFANT", ""+model.getInfantPassengerNum());
		map.put("__EVENTTARGET", "");
		map.put("__EVENTARGUMENT", "");
		map.put("pageToken", "");
		map.put("culture", "id-ID");
		map.put("promocode", "");
		map.put("ControlGroupCompactView$ButtonSubmit", model.getButtonSubmit());
		map.put("ControlGroupCompactView$AvailabilitySearchInputCompactView$DropDownListSearchBy", "columnView");
		map.put("__VIEWSTATE", "wEPDwUBMGRktapVDbdzjtpmxtfJuRZPDMU9XYk=");
		map.put("date_picker", model.getDatePicker1());
		map.put("date_picker", model.getDatePicker2());
		return map;
	}
	
	private Map<String, String> populateRequestDataMobile(FlightSearchAirAsiaModel model){
		Map<String, String> map = new HashMap<String, String>();
		map.put("hash", "997183b262cd6c3ccae3b79403c13d85");
		map.put("trip-type", model.getMarketStructure());
		map.put("origin", model.getOrigin());
		map.put("destination", model.getDestination());
		map.put("date-depart-d", model.getMarketDay1());
		map.put("date-depart-my", model.getMarketMonth1());
		map.put("date-return-d", model.getMarketDay2());
		map.put("date-return-my", model.getMarketMonth2());
		map.put("passenger-count", ""+model.getAdultPassengerNum());
		map.put("infant-count", ""+model.getInfantPassengerNum());
		map.put("currency", model.getCurrency());
		map.put("action", "search");
		map.put("btnSearch", "Pencarian");
		map.put("depart-sellkey", "");
		map.put("return-sellkey", "");
		map.put("depart-details-index", "");
		map.put("return-details-index", "");
		map.put("depart-faretype", "");
		map.put("return-faretype", "");
		return map;
	}
	
	/**
	 * provide common cookies for request
	 * @return
	 */
	private Map<String, String> populateCookies(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("optimizelySegments", "{\"192696121\":\"false\",\"192747026\":\"referral\",\"192747027\":\"ff\",\"192734033\":\"none\",\"196875818\":\"ff\",\"198133615\":\"referral\",\"198389126\":\"none\",\"197393471\":\"false\"}"); 
		map.put("optimizelyEndUserId", "oeu1371471357917r0.8329969913544446");
		map.put("optimizelyBuckets", "{}"); 
		map.put("__utma", "203916751.1251250523.1371471358.1387886280.1387890304.25"); 
		map.put("__utmz", "203916751.1387602060.14.6.utmcsr=airasia.com|utmccn=(referral)|utmcmd=referral|utmcct=/"); 
		map.put("s_rsid", "aa-airasia-id-prd"); 
		map.put("s_vi", "[CS]v1|28DF7EFF852A1A19-40000106C0004106[CE]"); 
		map.put("true_loc", "id"); 
		map.put("skysales", "1411047946.20480.0000"); 
		map.put("AKSB", "s=1387274017867&r=http://booking.airasia.com/Select.aspx"); 
		map.put("__utmc", "203916751"); 
		map.put("s_sess", " s_cc=true; s_sq=aa-airasia-id-prd,aa-airasia-global=&pid=www.airasia.com/id/id/home.page&pidt=1&oid=functiononclick(event){returnucSearchForm.buttonSearchNow_Click();}&oidt=2&ot=SUBMIT;"); 
		map.put("LanguageSelect", "id/id"); 
		map.put("ScheSTO", "true"); 
		map.put("__utmb", "203916751.2.10.1387890304");
		map.put("ASP.NET_SessionId", "lw1bdv55xsl2bpveo3pil2nw");
		map.put("aakau", "1387886928~id=8e8efb634da066198a8990e207fa9d6d");
		map.put("UserSessionExpired", "1");
		return map;
	}
	
	private Map<String, String> populateCookiesMobile(String currency){
		Map<String, String> map = new HashMap<String, String>();
		map.put("optimizelySegments", "{\"192696121\":\"false\",\"192747026\":\"referral\",\"192747027\":\"ff\",\"192734033\":\"none\",\"196875818\":\"ff\",\"198133615\":\"referral\",\"198389126\":\"none\",\"197393471\":\"false\"}"); 
		map.put("optimizelyEndUserId", "oeu1371471357917r0.8329969913544446");
		map.put("optimizelyBuckets", "{}"); 
		map.put("__utma", "203916751.1251250523.1371471358.1387949046.1387960110.27"); 
		map.put("__utmz", "203916751.1387602060.14.6.utmcsr=airasia.com|utmccn=(referral)|utmcmd=referral|utmcct=/"); 
		map.put("s_rsid", "aa-airasia-id-prd"); 
		map.put("s_vi", "[CS]v1|28DF7EFF852A1A19-40000106C0004106[CE]"); 
		map.put("true_loc", "id"); 
		map.put("skysales", "1411047946.20480.0000"); 
		map.put("AKSB", "s=1387274017867&r=http://booking.airasia.com/Select.aspx"); 
		map.put("__utmc", "203916751"); 
		map.put("s_sess", " s_cc=true; s_sq=;"); 
		map.put("LanguageSelect", "id/id"); 
		map.put("ScheSTO", "true"); 
		map.put("__utmb", "203916751.2.10.1387890304");
		map.put("AWSELB", "E93F9FF11CA7F06AEC1DE11C20D8C9F1C4B1B50F55E1CD94A5E92B766F8910447D6579E3802B026281FF12CCD9340AEFB23A3E002737A02B3B261589954AD42F075A322812");
		map.put("currency", currency);
		map.put("PHPSESSID", "o4rkl31mbb2e6t61n3vcrmfe78g9p1ji");
		map.put("__utma", "183325370.818437978.1387962074.1387962074.1387962074.1");
		map.put("__utmb", "183325370.2.10.1387962074");
		map.put("__utmc", "183325370");
		map.put("__utmz", "183325370.1387962074.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not provided)");
		return map;
	}
	
	public AirasiaWebConverter getAirasiaWebConverter() {
		return airasiaWebConverter;
	}

	public void setAirasiaWebConverter(AirasiaWebConverter airasiaWebConverter) {
		this.airasiaWebConverter = airasiaWebConverter;
	}

	public AirasiaMobileConverter getAirasiaMobileConverter() {
		return airasiaMobileConverter;
	}

	public void setAirasiaMobileConverter(
			AirasiaMobileConverter airasiaMobileConverter) {
		this.airasiaMobileConverter = airasiaMobileConverter;
	}
	
}
