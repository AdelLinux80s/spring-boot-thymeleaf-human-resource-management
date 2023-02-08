package org.hospital.hrms.controller;

import org.hospital.hrms.model.Employee;
import org.hospital.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository EmployeeRepo;
	
	
	
	@GetMapping("/employee/{employeeId}")
	public ModelAndView getOne(@PathVariable Long employeeId) {
		//ModelAndView mav = new ModelAndView("index - Copy");
		ModelAndView mav = new ModelAndView("employee-frontpage");

		
		Employee employee = EmployeeRepo.findById(employeeId).get();
		mav.addObject("employee", employee);
		return mav;
	}
	
	@GetMapping("/employee/profile/{employeeId}")
	public ModelAndView getProfile(@PathVariable Long employeeId) {
		
		//System.out.println(employeeId);
		Employee employee = EmployeeRepo.findById(employeeId).get();
		//System.out.println(employee);
		ModelAndView mav = new ModelAndView("employee-profile");
		mav.addObject("employee", employee);
		return mav;
	}
	
	
	
	@PostMapping("/employee/saveProfile")
	public String saveProfile(@ModelAttribute Employee employee) {
		
		//System.out.println("Employee form form: " +  employee);
		Employee tempEmployee = EmployeeRepo.findById(employee.getEmployeeId()).get();
		tempEmployee.setAddress(employee.getAddress());
		tempEmployee.setContactNumber(employee.getContactNumber());
		//System.out.println("tempEmployee form database: " +  tempEmployee);
		
		EmployeeRepo.save(tempEmployee);
		return "redirect:/employee/" + employee.getEmployeeId();
	}
	
	
}
