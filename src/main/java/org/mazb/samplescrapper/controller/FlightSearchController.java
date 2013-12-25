package org.mazb.samplescrapper.controller;

import org.mazb.samplescrapper.client.AirAsiaHttpClient;
import org.mazb.samplescrapper.model.FlightInfo;
import org.mazb.samplescrapper.model.FlightSearchAirAsiaModel;
import org.mazb.samplescrapper.model.FlightSearchModel;
import org.mazb.samplescrapper.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/search")
public class FlightSearchController {
	
	@Autowired(required=true)
	private ModelConverter modelConverter;
	
	@Autowired(required=true)
	private AirAsiaHttpClient airasiaHttpClient;
	
	@RequestMapping(value="/{airline}", method=RequestMethod.POST)
	public ModelAndView postToAirAsia(@PathVariable String airline, @ModelAttribute("flightSearchModel") FlightSearchModel flightSearchModel, final RedirectAttributes redirectAttributes){
		System.out.println("received: "+airline+", "+flightSearchModel);
		ModelAndView mav = new ModelAndView();
		FlightInfo flightInfo = null;
		if(airline.equals("airasia")){
			FlightSearchAirAsiaModel airAsiaModel = modelConverter.toFlightSearchAirAsiaModel(flightSearchModel);
			flightInfo = airasiaHttpClient.postToAirasiaMobile(airAsiaModel);
		}
		
		mav.setViewName("redirect:/search/result.html");
		redirectAttributes.addFlashAttribute("flightInfo", flightInfo);
		
		return mav;
	}
	
	@RequestMapping(value="/result", method=RequestMethod.GET)
	public String shopListPage(@ModelAttribute("flightInfo") FlightInfo flightInfo){
		System.out.println("redirecting to list page.");
		return "flightList";
	}
	
}
