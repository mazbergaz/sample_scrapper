package org.mazb.samplescrapper.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mazb.samplescrapper.client.AirAsiaHttpClient;
import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.mazb.samplescrapper.util.AirasiaMobileConverter;
import org.mazb.samplescrapper.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class ParserTest {
	
	@Autowired(required=true)
	private ModelConverter modelConverter;
	
	@Autowired(required=true)
	private AirAsiaHttpClient airasiaHttpClient;
	
	@Test
	public void postToAirAsiaMockupWeb(){
		FlightSearchAirAsiaModel airAsiaModel = new FlightSearchAirAsiaModel();
		airAsiaModel.setMarketStructure("RoundTrip");
		airAsiaModel.setDatePicker1("12/30/2013");
		airAsiaModel.setDatePicker2("12/31/2013");
		
	    File file = null;
		try {
			file = new ClassPathResource("test01.html").getFile();
			FlightInfo flightInfo = airasiaHttpClient.postToAirAsiaWebMockup(airAsiaModel, file);
			assertTrue(flightInfo.getGoFlightDetails().size()==8);
			assertTrue(flightInfo.getReturnFlightDetails().size()==8);

			file = new ClassPathResource("test03.html").getFile();
			flightInfo = airasiaHttpClient.postToAirAsiaWebMockup(airAsiaModel, file);
			assertTrue(flightInfo.getGoFlightDetails().size()==8);
			assertTrue(flightInfo.getReturnFlightDetails().size()==0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void postToAirAsiaMockupMobile(){
		FlightSearchAirAsiaModel airAsiaModel = new FlightSearchAirAsiaModel();
		airAsiaModel.setMarketStructure("round-trip");
		airAsiaModel.setDatePicker1("12/30/2013");
		airAsiaModel.setDatePicker2("12/31/2013");
		airAsiaModel.setCurrency("IDR");
		
	    File file = null;
		try {
			file = new ClassPathResource("postmobile_result.html").getFile();
			FlightInfo flightInfo = airasiaHttpClient.postToAirasiaMobileMockup(airAsiaModel, file);
			assertTrue(flightInfo.getGoFlightDetails().size()==9);
			assertTrue(flightInfo.getReturnFlightDetails().size()==9);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
