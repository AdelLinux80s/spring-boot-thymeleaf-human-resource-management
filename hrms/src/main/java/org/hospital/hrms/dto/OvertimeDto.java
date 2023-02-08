package org.hospital.hrms.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
public class OvertimeDto {

	private long overtimeId;
	private long employeeId;
	private int groupId;
	
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date timeIn;
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date timeOut;
	@DateTimeFormat(iso=ISO.DATE)
	private Date createdDate;
	@DateTimeFormat(iso=ISO.DATE)
	private Date overtimeIn;
	@DateTimeFormat(iso=ISO.DATE)
	private Date overtimeOut;
	private String approvedToString;
	
	private Boolean approved;
	private Boolean submitted;
	private Boolean cancelled;
	
	private Boolean isApproved;
	private Boolean isSubmitted;
	private String employeeName;
	private long supervisorId;
	
	
}
