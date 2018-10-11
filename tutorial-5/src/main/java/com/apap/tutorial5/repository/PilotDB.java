package com.apap.tutorial5.repository;

import com.apap.tutorial5.model.PilotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * PilotDB
 */
@Repository
public interface PilotDB extends JpaRepository<PilotModel, Long> {
	PilotModel findByLicenseNumber(String licenseNumber);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE pilot p SET p.name = :name WHERE p.license_number = :licenseNumber", nativeQuery = true)
	void updateName(@Param("name") String name, @Param("licenseNumber") String licenseNumber);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE pilot p SET p.fly_hour = :flyHour WHERE p.license_number = :licenseNumber", nativeQuery = true)
	void updateFlyHour(@Param("flyHour") int flyHour, @Param("licenseNumber") String licenseNumber);
}
