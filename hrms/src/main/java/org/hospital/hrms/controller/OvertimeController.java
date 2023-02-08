package org.hospital.hrms.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hospital.hrms.dto.OvertimeDto;
import org.hospital.hrms.model.Absence;
import org.hospital.hrms.model.Employee;
import org.hospital.hrms.model.Overtime;
import org.hospital.hrms.repository.EmployeeRepository;
import org.hospital.hrms.repository.OvertimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OvertimeController {

	@Autowired
	private OvertimeRepository overtimeRepo;
	
	@Autowired
	private EmployeeRepository  employeeRepo;
	
	@GetMapping("/overtime/employee/{employeeId}")
	public ModelAndView getOneOvertime(@PathVariable Long employeeId) {
		ModelAndView mav = new ModelAndView("employee-overtime");
		Employee employee = employeeRepo.findById(employeeId).get();
		Employee supervisor = employeeRepo.findByGroupSupervises(employee.getGroupId());
		List<Overtime> overtime = overtimeRepo.findByEmployeeId(employeeId);
		List<OvertimeDto> overtimeDto = overtimeToDto(overtime,supervisor.getEmployeeId());
		
		System.out.println("/overtime/employee/{employeeId} overtime: " + overtime);
		System.out.println("/overtime/employee/{employeeId} overtimeDto: " + overtimeDto);
		mav.addObject("overtimeDto", overtimeDto);
		mav.addObject("employee", employee);
		
		return mav;
		
	}
	
	
	@GetMapping("/overtime/employee/add/{employeeId}")
	public ModelAndView addOvertime(@PathVariable Long employeeId) {
		ModelAndView mav = new ModelAndView("add-overtime");
		Overtime overtime = new Overtime();
		Employee employee = employeeRepo.findById(employeeId).get();
		
		overtime.setEmployeeId(employee.getEmployeeId());
		
		mav.addObject("overtime", overtime);
		mav.addObject("employee", employee);
		
		return mav;
		
	}
	
	@GetMapping("/overtime/submit/{overtimeId}")
	public String submitOvertime(@PathVariable Long overtimeId) {
		Overtime overtime = overtimeRepo.findById(overtimeId).get();
		overtime.setSubmitted(true);
		overtimeRepo.save(overtime);
		
		return "redirect:/overtime/employee/" + overtime.getEmployeeId();
		
	}
	
	@GetMapping("/overtime/update/{overtimeId}")
	public ModelAndView updateOvertime(@PathVariable Long overtimeId) {
		
		ModelAndView mav = new ModelAndView("add-overtime");
		Overtime overtime = overtimeRepo.findById(overtimeId).get();
		Employee employee = employeeRepo.findById(overtime.getEmployeeId()).get();
		
		//overtime.setEmployeeId(employee.getEmployeeId());
		
		mav.addObject("overtime", overtime);
		mav.addObject("employee", employee);
		
		return mav;
		
	}
	
	@GetMapping("/overtime/delete/{overtimeId}")
	public String deleteOvertime(@PathVariable Long overtimeId) {
		
		
		Overtime overtime = overtimeRepo.findById(overtimeId).get();
		overtimeRepo.deleteById(overtimeId);
		
		return "redirect:/overtime/employee/" + overtime.getEmployeeId();
		
	}
	
	@PostMapping("/overtime")
	public String postOneOvertime(@ModelAttribute Overtime overtime) {
		
		
		
		Calendar calendarOriginal = Calendar.getInstance();
		calendarOriginal.setTime(overtime.getOvertimeIn());
		System.out.println(" PostMapping calendarOriginal" + calendarOriginal);
		
		Calendar timeIn = Calendar.getInstance();
		timeIn.setTime(overtime.getTimeIn());
		
		System.out.println(" PostMapping timeIn" + timeIn);
		
		Calendar calendarToBeModified = Calendar.getInstance();
		calendarToBeModified.set(calendarOriginal.get(Calendar.YEAR), calendarOriginal.get(Calendar.MONTH), calendarOriginal.get(Calendar.DATE), timeIn.get(Calendar.HOUR_OF_DAY), timeIn.get(Calendar.MINUTE), timeIn.get(Calendar.MILLISECOND));
		
		
		System.out.println(" PostMapping calendarToBeModified" + calendarToBeModified);
		
		Date myDate = calendarToBeModified.getTime();
		
		overtime.setOvertimeIn(myDate);
		overtimeRepo.save(overtime);
		
		
		return "redirect:/overtime/employee/" + overtime.getEmployeeId();
	}
	
	
	public List<OvertimeDto> overtimeToDto(List<Overtime> overtime, Long supervisorId){
		
		List<OvertimeDto> listDto =  new ArrayList<>();
		
		for(Overtime tempOvertime:overtime) {
			
			OvertimeDto overtimeDto = overtimeToDto(tempOvertime);
			overtimeDto.setSupervisorId(supervisorId);
			listDto.add(overtimeDto);
		}
		
		return listDto;
		
		
	}
	
	
	public OvertimeDto overtimeToDto(Overtime overtime) {
		
		OvertimeDto overtimeDto = new OvertimeDto();
		Employee employee = employeeRepo.findById(overtime.getEmployeeId()).get();
		overtimeDto.setOvertimeId(overtime.getOvertimeId());
		overtimeDto.setEmployeeId(overtime.getEmployeeId());
		overtimeDto.setGroupId(overtime.getGroupId());
		overtimeDto.setTimeIn(overtime.getTimeIn());
		overtimeDto.setTimeOut(overtime.getTimeOut());
		overtimeDto.setCreatedDate(overtime.getCreatedDate());
		overtimeDto.setOvertimeIn(overtime.getOvertimeIn());
		overtimeDto.setOvertimeOut(overtime.getOvertimeOut());
		overtimeDto.setApprovedToString(getApprovedToString(overtime.getApproved()));
		overtimeDto.setApproved(overtime.getApproved());
		overtimeDto.setSubmitted(overtime.getSubmitted());
		overtimeDto.setCancelled(overtime.getCancelled());
		overtimeDto.setIsApproved(isApprovedNull(overtime.getApproved()));
		overtimeDto.setIsSubmitted(isSubmitted(overtime.getSubmitted()));
		overtimeDto.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
		
	
		return overtimeDto;
		
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
