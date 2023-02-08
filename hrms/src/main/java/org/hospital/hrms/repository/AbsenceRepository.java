package org.hospital.hrms.repository;

import java.util.List;

import org.hospital.hrms.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AbsenceRepository extends JpaRepository<Absence, Long>{

	@Query("FROM Absence  WHERE employeeId = ?1")
    List<Absence> findByEmployeeId(Long employeeId);
	
	@Query("FROM Absence  WHERE groupId = ?1")
    List<Absence> findByGroupId(int groupId);
	
	
	
}
