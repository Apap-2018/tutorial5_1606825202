package com.apap.tutorial5.service;

import java.sql.Date;
import java.util.List;

import com.apap.tutorial5.model.FlightModel;

/**
 * FlightService
 */
public interface FlightService {
	void addFlight(FlightModel flight);

	FlightModel getFlightById(long id);
	void deleteFlight(FlightModel flight);

	void updateFlight(long id, String destination, String flightNumber, String origin, Date time);
	
	List<FlightModel> viewAll();
	
	void deleteFlightById(long id);
}
