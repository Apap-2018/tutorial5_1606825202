package com.apap.tutorial5.repository;

import com.apap.tutorial5.model.FlightModel;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * FlightDB
 */
@Repository
public interface FlightDB extends JpaRepository<FlightModel, Long> {
	FlightModel findById(long id);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE flight f SET f.destination= :destination WHERE f.id = :id", nativeQuery = true)
	void updateDestination(@Param("destination") String destination, @Param("id") long id);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE flight f SET f.flight_number= :flightNumber WHERE f.id = :id", nativeQuery = true)
	void updateFlightNumber(@Param("flightNumber") String flightNumber, @Param("id") long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE flight f SET f.origin= :origin WHERE f.id = :id", nativeQuery = true)
	void updateOrigin(@Param("origin") String origin, @Param("id") long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE flight f SET f.time= :time WHERE f.id = :id", nativeQuery = true)
	void updateTime(@Param("time") Date time, @Param("id") long id);
	
}
