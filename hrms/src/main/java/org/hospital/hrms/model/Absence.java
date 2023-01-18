package org.hospital.hrms.model;

import java.util.Date;


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
	private Date startingDate;
	private Date endingDate;
	private Boolean approved;
}
