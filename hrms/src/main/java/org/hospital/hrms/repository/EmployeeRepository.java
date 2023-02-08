package org.hospital.hrms.repository;

import java.util.List;

import org.hospital.hrms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	/*
	 * @Query("FROM Employee  WHERE groupSupervises=?1") Employee
	 * findByGroupSupervises(Long groupSupervises);
	 */
	
	@Query("FROM Employee  WHERE groupId=?1")
	List<Employee> findByGroupId(Long groupId);
	
	
	@Query("FROM Employee  WHERE groupSupervises=?1")
	Employee findByGroupSupervises(int groupSupervises);
	
	

}

