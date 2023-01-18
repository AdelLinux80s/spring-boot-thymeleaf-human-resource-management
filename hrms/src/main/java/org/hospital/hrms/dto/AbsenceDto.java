package org.hospital.hrms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AbsenceDto {

	
	private long absenceId;
	private long employeeId;
	private Date startingDate;
	private Date endingDate;
	private Boolean approved;
	private String approvedToString;
	
	
	
}
