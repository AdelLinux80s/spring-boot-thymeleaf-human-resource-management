package org.hospital.hrms.controller;

import java.util.ArrayList;
import java.util.List;

import org.hospital.hrms.dto.AbsenceDto;
import org.hospital.hrms.model.Absence;
import org.hospital.hrms.model.Employee;
import org.hospital.hrms.repository.AbsenceRepository;
import org.hospital.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AbsenceController {

	@Autowired
	private  AbsenceRepository absenceRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@GetMapping("/absence/employee/{employeeId}")
	public ModelAndView getAllAbsence(@PathVariable Long employeeId) {
		ModelAndView mav = new ModelAndView("employee-absence");
		List<Absence> absence = absenceRepo.findByEmployeeId(employeeId);
		//System.out.println("absence: " + absence);
		List<AbsenceDto> absenceDto = getAbsenceDto(absence, (long) 0);
		//System.out.println("absenceDto: " + absenceDto);
		mav.addObject("absenceDto", absenceDto);
		mav.addObject("employeeId", employeeId);
		return mav;
	}
	
	@GetMapping("/absence/employee/add/{employeeId}")
	public ModelAndView addAbsence(@PathVariable Long employeeId) {
		ModelAndView mav = new ModelAndView("add-absence");
		//Absence absence = new Absence();
		AbsenceDto absenceDto = new AbsenceDto();
		
		Employee employee = employeeRepo.findById(employeeId).get();
		absenceDto.setEmployeeId(employeeId);
		//absence.setEmployeeId(employeeId);
		
		//System.out.println("/absence/employee/add/{employeeId} absenceDto: " + absenceDto);
		
		mav.addObject("absenceDto", absenceDto);
		mav.addObject("employee", employee);
		
		return mav;
		
	}
	
	@GetMapping("/absence/update/{absenceId}")
	public ModelAndView updateAbsence(@PathVariable Long absenceId) {
		ModelAndView mav = new ModelAndView("add-absence");
		Absence absence = absenceRepo.findById(absenceId).get();
		AbsenceDto absenceDto = getAbsenceDto(absence);
		Employee employee = employeeRepo.findById(absenceDto.getEmployeeId()).get();
		
		mav.addObject("absenceDto", absenceDto);
		mav.addObject("employee", employee);
		//System.out.println("/absence/update/{absenceId} absenceDto: " + absenceDto);
		return mav;
	}
	
	@GetMapping("/absence/delete/{absenceId}")
		public String deleteAbsence(@PathVariable Long absenceId) {
			Absence absence  = absenceRepo.findById(absenceId).get();
			absenceRepo.deleteById(absenceId);
			
			return "redirect:/absence/employee/" + absence.getEmployeeId();
	
	}
	
	
	
	
	@GetMapping("/absence/supervisor/{employeeId}")
	public ModelAndView getAbsenceBySupervisor(@PathVariable Long employeeId){
		
		ModelAndView mav = new ModelAndView("supervisor-absence-mangement");
		Employee supervisor =  employeeRepo.findById(employeeId).get();
		//System.out.println("/absence/supervisor/{employeeId}   Absence supervisor :" + supervisor);
		//System.out.println("/absence/supervisor/{employeeId}   supervisor.getGroupSupervises() :" + supervisor.getGroupSupervises());
		List<Absence> absences = absenceRepo.findByGroupId(supervisor.getGroupSupervises());
		List<AbsenceDto> absenceDto = getAbsenceDto(absences, supervisor.getEmployeeId());
		
		System.out.println("/absence/supervisor/{employeeId}   absenceDto:" + absenceDto);
		//System.out.println("/absence/supervisor/{employeeId}   Employee supervisor:" + supervisor);
		mav.addObject("supervisor", supervisor);
		mav.addObject("absenceDto", absenceDto);
		mav.addObject("supervisor", supervisor);
		
		return mav;
		
		
	}
	
	@GetMapping("/absence/supervisor/approve/{supervisorId}/{absenceId}")
	public String approveAbsence(@PathVariable Long absenceId, @PathVariable Long supervisorId) {
		Absence absence = absenceRepo.findById(absenceId).get();
		absence.setApproved(true);
		absenceRepo.save(absence);
		
		//System.out.println("absence/supervisor/approve/{absenceId} supervisor: " + supervisor);
		
		//return "redirect:/absence/supervisor/" + absence.getEmployeeId();
		return "redirect:/absence/supervisor/" + supervisorId;
		
	}
	@GetMapping("/absence/supervisor/reject/{supervisorId}/{absenceId}")
	public String rejectAbsence(@PathVariable Long absenceId, @PathVariable Long supervisorId) {
		Absence absence = absenceRepo.findById(absenceId).get();
		absence.setApproved(false);
		absenceRepo.save(absence);
		
		return "redirect:/absence/supervisor/" + supervisorId;
	}
	    
	@GetMapping("/absence/supervisor/edit/{supervisorId}/{absenceId}")
	public String editAbsence(@PathVariable Long absenceId, @PathVariable Long supervisorId) {
		Absence absence = absenceRepo.findById(absenceId).get();
		absence.setApproved(null);
		absenceRepo.save(absence);
		
		return "redirect:/absence/supervisor/" + supervisorId;
	}
	
	
	
	@GetMapping("/absence/submit/{absenceId}")
	public String submitForAbsence(@PathVariable Long absenceId) {
		Absence absence = absenceRepo.findById(absenceId).get();
		absence.setSubmitted(true);
		absenceRepo.save(absence);
		
		return "redirect:/absence/employee/" + absence.getEmployeeId();
	}
	
	@PostMapping("/absence")
	//public String postOneAbsence(@ModelAttribute Absence absence,@ModelAttribute Employee employee ,long employeeId) {
	//public String postOneAbsence(@ModelAttribute Absence absence) {
	public String postOneAbsence(@ModelAttribute AbsenceDto absenceDto) {
	
		//System.out.println("/absence/ absenceDto: " + absenceDto);
		//System.out.println("employee.employeeId: " + employeeId);
		Employee employee = employeeRepo.findById(absenceDto.getEmployeeId()).get();
		absenceDto.setGroupId(employee.getGroupId());
		Absence absence = getAbsence(absenceDto);
		
		
		
		
		//System.out.println("tempAbsence: " + absence);
		
		absenceRepo.save(absence);
		
		return "redirect:/absence/employee/" + absence.getEmployeeId();
	}
	
	public List<AbsenceDto> getAbsenceDto(List<Absence> absence, Long supervisorId) {
		List<AbsenceDto> listDto =new ArrayList<AbsenceDto>();
		
		
		for(Absence tempAbsence:absence) {
			Employee employee = employeeRepo.findById(tempAbsence.getEmployeeId()).get();
			AbsenceDto absenceDto = new AbsenceDto();
			absenceDto.setAbsenceId(tempAbsence.getAbsenceId());
			absenceDto.setEmployeeId(tempAbsence.getEmployeeId());
			absenceDto.setStartingDate(tempAbsence.getStartingDate());
			absenceDto.setEndingDate(tempAbsence.getEndingDate());
			absenceDto.setApproved(tempAbsence.getApproved());
			absenceDto.setGroupId(tempAbsence.getGroupId());
			absenceDto.setApprovedToString(getApprovedToString(tempAbsence.getApproved()));
			absenceDto.setSubmitted(tempAbsence.getSubmitted());
			absenceDto.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
			absenceDto.setIsApproved(isApprovedNull(tempAbsence.getApproved()));
			absenceDto.setIsSubmitted(isSubmitted(tempAbsence.getSubmitted()));
			absenceDto.setSupervisorId(supervisorId);
			
			//System.out.println("absenceDto" + absenceDto);
			
			listDto.add(absenceDto);
		}
		
		return listDto;
		
		 
	}
	
	public AbsenceDto getAbsenceDto(Absence absence) {
		
		AbsenceDto absenceDto = new AbsenceDto();
		absenceDto.setAbsenceId(absence.getAbsenceId());
		absenceDto.setEmployeeId(absence.getEmployeeId());
		absenceDto.setGroupId(absence.getGroupId());
		absenceDto.setStartingDate(absence.getStartingDate());
		absenceDto.setEndingDate(absence.getEndingDate());
		absenceDto.setApproved(absence.getApproved());
		absenceDto.setSubmitted(absence.getSubmitted());
		//absenceDto.setRejected(absence.getRejected());
		absenceDto.setCancelled(absence.getCancelled());
		absenceDto.setApprovedToString(getApprovedToString(absence.getApproved()));
		absenceDto.setIsApproved(isApprovedNull(absence.getApproved()));
		absenceDto.setIsSubmitted(isSubmitted(absence.getSubmitted()));
		System.out.println("absenceDto.setAction(action(absence.getApproved(), absence.getRejected())); : ");
		return absenceDto;
	}
	
	public Absence getAbsence(AbsenceDto absenceDto) {
		
		Absence absence =new Absence();
		absence.setAbsenceId(absenceDto.getAbsenceId());
		absence.setGroupId(absenceDto.getGroupId());
		absence.setEmployeeId(absenceDto.getEmployeeId());
		absence.setStartingDate(absenceDto.getStartingDate());
		absence.setEndingDate(absenceDto.getEndingDate());
		absence.setApproved(absenceDto.getApproved());
		
		return absence;
	}

	private String getApprovedToString(Boolean approved) {
		if(approved == null ) {
			return "Pending";
		}
		
		if(approved == false) {
			return "Rejected";
		}
		
		else 
		return "Approved";
	}
	
	private Boolean isApprovedNull(Boolean approved) {
		if(approved == null) {
			System.out.println("in action false");
			return false;
		}
		
		else
			return true;
	}
	
	private Boolean isSubmitted(Boolean submitted) {
		if(submitted == null || submitted == false) {
			return false;
		}
		else
			return true;
	}
	
	
	
}
