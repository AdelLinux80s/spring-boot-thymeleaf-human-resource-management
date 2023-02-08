package org.hospital.hrms.repository;

import java.util.List;

import org.hospital.hrms.model.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OvertimeRepository extends JpaRepository<Overtime, Long>{

	@Query("FROM Overtime  WHERE employeeId = ?1")
	List<Overtime> findByEmployeeId(Long employeeId);
}
