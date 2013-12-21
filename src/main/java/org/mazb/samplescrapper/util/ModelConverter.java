package org.mazb.samplescrapper.util;

import java.text.SimpleDateFormat;

import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.mazb.samplescrapper.model.FlightSearchModel;
import org.springframework.stereotype.Service;

@Service
public class ModelConverter {
	
	public FlightSearchAirAsiaModel toFlightSearchAirAsiaModel(FlightSearchModel flightSearchModel){
		FlightSearchAirAsiaModel flightSearchAirAsiaModel = new FlightSearchAirAsiaModel();
		flightSearchAirAsiaModel.setOrigin(flightSearchModel.getOrigin());
		flightSearchAirAsiaModel.setDestination(flightSearchModel.getDestination());
		flightSearchAirAsiaModel.setCurrency(flightSearchModel.getCurrency()==null ? "default" : flightSearchModel.getCurrency());
		SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
		flightSearchAirAsiaModel.setMarketDay1(dayFormatter.format(flightSearchModel.getDepartureDate()));
		flightSearchAirAsiaModel.setMarketDay2(dayFormatter.format(flightSearchModel.getArrivalDate()));
		SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy-MM");
		flightSearchAirAsiaModel.setMarketMonth1(monthFormatter.format(flightSearchModel.getDepartureDate()));
		flightSearchAirAsiaModel.setMarketMonth2(monthFormatter.format(flightSearchModel.getArrivalDate()));
		flightSearchAirAsiaModel.setMarketStructure(flightSearchModel.getTripType());
		flightSearchAirAsiaModel.setAdultPassengerNum(""+flightSearchModel.getPassengerAdultNum());
		flightSearchAirAsiaModel.setChildrenPassengerNum(""+flightSearchModel.getPassengerChildrenNum());
		flightSearchAirAsiaModel.setInfantPassengerNum(""+flightSearchModel.getPassengerInfantNum());
		flightSearchAirAsiaModel.setButtonSubmit("Search");
		SimpleDateFormat datePickerFormatter = new SimpleDateFormat("MM/dd/yyyy");
		flightSearchAirAsiaModel.setDatePicker1(datePickerFormatter.format(flightSearchModel.getDepartureDate()));
		flightSearchAirAsiaModel.setDatePicker2(datePickerFormatter.format(flightSearchModel.getArrivalDate()));
		return flightSearchAirAsiaModel;
	}
	
}
