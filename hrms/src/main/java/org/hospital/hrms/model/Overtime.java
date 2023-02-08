package org.hospital.hrms.model;



import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name="overtime")
@Data
public class Overtime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long overtimeId;
	private long employeeId;
	private int groupId;
	//private String timeIn;
	
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
	
	private Boolean approved;
	private Boolean submitted;
	private Boolean cancelled;
}
