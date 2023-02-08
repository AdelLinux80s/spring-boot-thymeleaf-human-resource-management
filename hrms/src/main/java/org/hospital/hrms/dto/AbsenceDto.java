package org.hospital.hrms.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class AbsenceDto {

	
	private long absenceId;
	private long employeeId;
	private int groupId;
	@DateTimeFormat(iso=ISO.DATE)
	private Date startingDate;
	@DateTimeFormat(iso=ISO.DATE)
	private Date endingDate;
	private Boolean approved;
	private Boolean submitted;
	//private Boolean rejected;
	private Boolean cancelled;
	private String approvedToString;
	private Boolean isApproved;
	private Boolean isSubmitted;
	private String employeeName;
	private long supervisorId;
	
	
	
}
