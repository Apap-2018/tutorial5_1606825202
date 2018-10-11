package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FlightController
 * @author hauri
 */
@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		ArrayList<FlightModel> flightList = new ArrayList<>();
		pilot.setPilotFlight(flightList);
		flightList.add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "APAP | Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
	private String addFligthSubmit(@ModelAttribute PilotModel pilot, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for(FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(archive);
			flightService.addFlight(flight);
		}
		model.addAttribute("title", "APAP");
		return "add";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight() ==  null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		pilot.getPilotFlight().add(new FlightModel());
	    model.addAttribute("pilot", pilot);
	    return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"removeRow"}, method = RequestMethod.POST)
	public String removeRow(PilotModel pilot, BindingResult bindingResult, HttpServletRequest req, Model model) {
	   Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	   pilot.getPilotFlight().remove(rowId.intValue());
	   model.addAttribute("pilot", pilot);
	   return "addFlight";
	}

	@RequestMapping(value="/flight/delete", method=RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		model.addAttribute("title", "Delete Flight");
		return "delete";
	}
	
	@RequestMapping(value = "flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable(value = "id") long id, Model model){
		FlightModel flight = flightService.getFlightById(id);
		PilotModel pilot = flight.getPilot();
		
		FlightModel newflight = new FlightModel();
		newflight.setId(id);
		newflight.setPilot(pilot);
		
		model.addAttribute("title", "Update FLight");
		model.addAttribute("newFlight", newflight);
		return "updateFlight";
	}
	
	@RequestMapping(value = "/flight/update", method = RequestMethod.POST)
	private String updateSubmit(@ModelAttribute FlightModel newFlight) {
		long id = newFlight.getId();
		String destination = newFlight.getDestination();
		String flightNumber = newFlight.getFlightNumber();
		String origin = newFlight.getOrigin();
		Date time = newFlight.getTime();
		
		flightService.updateFlight(id, destination, flightNumber, origin, time);
		
		return "update";
	}
	
	@RequestMapping(value = "/flight/viewall")
	private String viewAll(Model model) {
		model.addAttribute("flightList", flightService.viewAll());
		
		model.addAttribute("title", "View All Flight");
		return "viewAll";
	}
}
