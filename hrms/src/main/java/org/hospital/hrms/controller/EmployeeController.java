package org.hospital.hrms.controller;

import org.hospital.hrms.model.Employee;
import org.hospital.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository EmployeeRepo;
	
	@GetMapping("/employee/{id}")
	public ModelAndView getOne(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("employee-frontpage");
		Employee employee = EmployeeRepo.findById(id).get();
		mav.addObject("employee", employee);
		return mav;
	}
	
	@GetMapping("/employee/profile")
	public ModelAndView getProfile(@RequestParam Long employeeId) {
		
		System.out.println(employeeId);
		Employee employee = EmployeeRepo.findById(employeeId).get();
		System.out.println(employee);
		ModelAndView mav = new ModelAndView("employee-profile");
		mav.addObject("employee", employee);
		return mav;
	}
	
}
