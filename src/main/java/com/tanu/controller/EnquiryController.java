package com.tanu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tanu.binding.AddEnquiryForm;
import com.tanu.binding.EditEnquiryForm;
import com.tanu.binding.ViewEnquirySearchCriteria;
import com.tanu.entity.StudentEnquiryEntity;
import com.tanu.response.DashboardResponse;
import com.tanu.service.StudentEnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private StudentEnquiryService studentService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logOut() {
		session.invalidate();
		return "index";

	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		DashboardResponse dashboard = studentService.getDashboard(userId);
		model.addAttribute("dashboard", dashboard);
		return "dashboard";

	}

	@GetMapping("/addEnq")
	public String addEnquiryPage(Model model) {

		init(model);
		// create binding form obj
		AddEnquiryForm addEnqForm = new AddEnquiryForm();

		model.addAttribute("enqForm", addEnqForm);

		return "add-enquiry";

	}

	@PostMapping("/addEnq")
	public String addEnquiryHandler(@ModelAttribute("enqForm") AddEnquiryForm addEnquiryForm, Model model) {

		boolean status = studentService.saveStudentEnquiry(addEnquiryForm);

		if (status) {
			model.addAttribute("successMsg", "Enquiry insertion successfully");
		} else {
			model.addAttribute("errorMsg", "Insertion failed");
		}

		return "add-enquiry";
	}


	private void init(Model model) {
		// get all courses for drop down
		List<String> courses = studentService.getAllCourses();

		// get all status for drop down
		List<String> statuses = studentService.getAllStatuses();

		// set data to model
		model.addAttribute("courseList", courses);
		model.addAttribute("enqStatuslist", statuses);
	}
	

	@GetMapping("/viewEnq")
	public String viewEnquiryPage(Model model) {

		init(model);
		List<StudentEnquiryEntity> allData = studentService.getAllData();
		model.addAttribute("studentEnqData", allData);

		return "view-enquiries";

	}
	

	@GetMapping("/viewEnq-Filter")
	public String viewEnquiryFilterCriteria( @RequestParam String courseName,
			                                @RequestParam String enqStatus,
			                                @RequestParam String classMode,
			                                Model model) {

		init(model);
		  ViewEnquirySearchCriteria criteria= new   ViewEnquirySearchCriteria();
		criteria.setCourseName(courseName);
		criteria.setEnqStatus(enqStatus);
		criteria.setClassMode(classMode);
		Integer userId =(Integer) session.getAttribute("userId");
		List<StudentEnquiryEntity> enquiryFilterCriteria = studentService.enquiryFilterCriteria(criteria, userId);
		model.addAttribute("studentEnqData", enquiryFilterCriteria);

		return "view-enquiries-filter";

	}
	@GetMapping("/editEnquiry")
	public String editStudentEnquiry(@RequestParam("enquiryId") Integer enquiryId, Model model) {
		
		init(model); 
		Integer userId =(Integer) session.getAttribute("userId");
		StudentEnquiryEntity editEnquiry = studentService.editEnquiry(enquiryId);
		model.addAttribute("editData", editEnquiry);
		
		return "edit-enquiry";
	}
	
	
	  @PostMapping("/updateEnquiry") 
	  public String updateStudentEnquiry(@ModelAttribute("editData") EditEnquiryForm enquiryForm,Model model) {
	  
	  init(model); 
	  Integer userId =(Integer) session.getAttribute("userId");
	  boolean status = studentService.updateEnquiry(enquiryForm);
	  if (status) {
		  model.addAttribute("successMsg", "Enquiry updation successfully");
		  } else {
	      model.addAttribute("errorMsg", "updation failed"); }
	  
	  
	  return "edit-enquiry"; 
	  }
	  
	 
}
