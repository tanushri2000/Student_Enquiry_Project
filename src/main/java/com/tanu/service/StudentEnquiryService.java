package com.tanu.service;

import java.util.List;

import com.tanu.binding.AddEnquiryForm;
import com.tanu.binding.EditEnquiryForm;
import com.tanu.binding.ViewEnquirySearchCriteria;
import com.tanu.entity.StudentEnquiryEntity;
import com.tanu.response.DashboardResponse;

public interface StudentEnquiryService {
	

	public List<String> getAllCourses();
	
	public List<String> getAllStatuses();

	public boolean saveStudentEnquiry(AddEnquiryForm enquiryForm);
	
	public DashboardResponse getDashboard(Integer userId);

	public List<StudentEnquiryEntity> getAllData();
	
	public List<StudentEnquiryEntity> enquiryFilterCriteria(ViewEnquirySearchCriteria criteria,Integer userId);
	
	public StudentEnquiryEntity editEnquiry(Integer enquiryId);
	
	public boolean updateEnquiry(EditEnquiryForm enquiryForm);
}
