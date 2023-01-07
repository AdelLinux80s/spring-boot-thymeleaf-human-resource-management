package org.hospital.hrms.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="employee")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	private String firstName;
	private String lastName;
	private String address;
	private String contactNumber;
	private String emailAddress;
	private Date dob;
	private String occupation;
	private String alias;
	private Date dateOfHire;
	private String department;
	private int userTypeId;
	private int groupId;
}
