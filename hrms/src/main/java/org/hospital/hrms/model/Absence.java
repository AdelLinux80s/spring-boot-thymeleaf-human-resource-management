package org.hospital.hrms.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="absence")
@Data
public class Absence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
}
