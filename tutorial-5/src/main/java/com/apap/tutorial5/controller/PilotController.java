package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;

import java.awt.print.Printable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * PilotController
 */
@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "APAP");
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("title", "Add Pilot");
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	private String viewPilotByLicenseNumber(@RequestParam(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "View Pilot");
		return "view-pilot";
	}
	
	@RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
	private String deletePilotByLicenseNumber(@PathVariable(value = "licenseNumber") String licenseNumber) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilotService.deletePilot(pilot);
		return "delete";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {		
		PilotModel newPilot = new PilotModel();
		newPilot.setLicenseNumber(licenseNumber);
		
		model.addAttribute("title", "Update Pilot");
		model.addAttribute("newPilot", newPilot);
		return "updatePilot";
	}
	
	@RequestMapping(value = "/pilot/update", method = RequestMethod.POST)
	private String updateSubmit(@ModelAttribute PilotModel newPilot, Model model) {
		String licenseNumber = newPilot.getLicenseNumber();
		String name = newPilot.getName();
		int flyHour = newPilot.getFlyHour();
		
		pilotService.updatePilot(licenseNumber, name, flyHour);
		return "update";
	}

}
