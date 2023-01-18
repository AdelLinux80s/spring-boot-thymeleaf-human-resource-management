package org.hospital.hrms.controller;

import java.util.ArrayList;
import java.util.List;

import org.hospital.hrms.dto.AbsenceDto;
import org.hospital.hrms.model.Absence;
import org.hospital.hrms.model.Employee;
import org.hospital.hrms.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AbsenceController {

	@Autowired
	private  AbsenceRepository absenceRepo;
	
	@GetMapping("/absence/employee/{employeeId}")
	public ModelAndView getAllAbsence(@PathVariable Long employeeId) {
		ModelAndView mav = new ModelAndView("employee-absence");
		List<Absence> absence = absenceRepo.findByEmployeeId(employeeId);
		//System.out.println("absence: " + absence);
		List<AbsenceDto> absenceDto = getAbsenceDto(absence);
		System.out.println("absenceDto: " + absenceDto);
		mav.addObject("absenceDto", absenceDto);
		mav.addObject("employeeId", employeeId);
		return mav;
	}
	
	public List<AbsenceDto> getAbsenceDto(List<Absence> absence) {
		List<AbsenceDto> listDto =new ArrayList<AbsenceDto>();
		
		
		for(Absence tempAbsence:absence) {
			AbsenceDto absenceDto = new AbsenceDto();
			absenceDto.setAbsenceId(tempAbsence.getAbsenceId());
			absenceDto.setEmployeeId(tempAbsence.getEmployeeId());
			absenceDto.setStartingDate(tempAbsence.getStartingDate());
			absenceDto.setEndingDate(tempAbsence.getEndingDate());
			absenceDto.setApproved(tempAbsence.getApproved());
			//absenceDto.set
			absenceDto.setApprovedToString(getApprovedToString(tempAbsence.getApproved()));
			
			
			System.out.println("absenceDto" + absenceDto);
			
			listDto.add(absenceDto);
		}
		
		return listDto;
		
		 
	}

	private String getApprovedToString(Boolean approved) {
		if(approved == null || approved == false) {
			return "No";
		}
		
		else 
		return "Yes";
	}
	
	
	
}
