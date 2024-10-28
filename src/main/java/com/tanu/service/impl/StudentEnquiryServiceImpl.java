package com.tanu.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanu.binding.AddEnquiryForm;
import com.tanu.binding.EditEnquiryForm;
import com.tanu.binding.ViewEnquirySearchCriteria;
import com.tanu.entity.StudentEnquiryEntity;
import com.tanu.entity.UserDetailsEntity;
import com.tanu.repo.CourseNameRepo;
import com.tanu.repo.EnquiryStatusRepo;
import com.tanu.repo.StudentEnquiryRepo;
import com.tanu.repo.UserDetailsRepo;
import com.tanu.response.DashboardResponse;
import com.tanu.service.StudentEnquiryService;

@Service
public class StudentEnquiryServiceImpl implements StudentEnquiryService {

	@Autowired
	private CourseNameRepo courseNameRepo;

	@Autowired
	private EnquiryStatusRepo enquiryStatusRepo;

	@Autowired
	private StudentEnquiryRepo studentEnqRepo;

	@Autowired
	private UserDetailsRepo userRepo;

	@Autowired
	private HttpSession session;

	@Override
	public List<String> getAllCourses() {

		List<String> courseName = courseNameRepo.getCourseName();
		return courseName;

	}

	@Override
	public List<String> getAllStatuses() {

		List<String> enquiryStatus = enquiryStatusRepo.getEnquiryStatus();
		return enquiryStatus;

	}

	@Override
	public boolean saveStudentEnquiry(AddEnquiryForm enquiryForm) {

		// copy binding obj to entity obj
		StudentEnquiryEntity studentEntity = new StudentEnquiryEntity();
		BeanUtils.copyProperties(enquiryForm, studentEntity);

		// get user id from session
		Integer userId = (Integer) session.getAttribute("userId");

		// from that id get user entity data
		UserDetailsEntity userEntity = userRepo.findById(userId).get();

		// set that user data into student enq entity
		studentEntity.setUser(userEntity);

		// insert data
		studentEnqRepo.save(studentEntity);

		return true;
	}

	@Override
	public DashboardResponse getDashboard(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();

			List<StudentEnquiryEntity> studentEnquiry = userEntity.getStudentEnquiry();

			Integer totalEnquiriesCount = studentEnquiry.size();

			Integer entrolledCount = studentEnquiry.stream().filter(e -> e.getEnqStatus().equals("Entrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCount = studentEnquiry.stream().filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiriesCount(totalEnquiriesCount);
			response.setEntrolledCount(entrolledCount);
			response.setLostCount(lostCount);

		}

		return response;
	}

	@Override
	public List<StudentEnquiryEntity> getAllData() {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();

			List<StudentEnquiryEntity> studentEnquiry = userEntity.getStudentEnquiry();
			return studentEnquiry;
		}

		return null;

	}

	@Override
	public List<StudentEnquiryEntity> enquiryFilterCriteria(ViewEnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();

			List<StudentEnquiryEntity> studentEnquiry = userEntity.getStudentEnquiry();
			
			//filter
			if(null!=criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
			 studentEnquiry= studentEnquiry.stream()
				              .filter(e->e.getCourseName().equals(criteria.getCourseName()))
				              .collect(Collectors.toList());
			}
			
			if(null!=criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {
			studentEnquiry = studentEnquiry.stream()
				              .filter(e->e.getEnqStatus().equals(criteria.getEnqStatus()))
				              .collect(Collectors.toList());
			}
			
			if(null!=criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
			 studentEnquiry = studentEnquiry.stream()
				              .filter(e->e.getClassMode().equals(criteria.getClassMode()))
				              .collect(Collectors.toList());
			}
			
			return studentEnquiry;
		}
		return null;
	}

	@Override
	public StudentEnquiryEntity editEnquiry(Integer enquiryId) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			
			List<StudentEnquiryEntity> studentEnquiry = userDetailsEntity.getStudentEnquiry();
			
			Optional<StudentEnquiryEntity> byId = studentEnqRepo.findById(enquiryId);{
			if(	byId.isPresent()) {
				StudentEnquiryEntity studentEnquiryEntity = byId.get();
				return studentEnquiryEntity;
			}
			}
		}
	
		return null;
	     
		
	}

	@Override
	public boolean updateEnquiry(EditEnquiryForm enquiryForm) {
		
		// copy binding obj to entity obj
		StudentEnquiryEntity studentEntity = new StudentEnquiryEntity();
		BeanUtils.copyProperties(enquiryForm, studentEntity);
		
		
		// get user id from session
		Integer userId = (Integer) session.getAttribute("userId");

		// from that id get user entity data
		UserDetailsEntity userEntity = userRepo.findById(userId).get();

		
		// set that user data into student enq entity
		studentEntity.setUser(userEntity);

		// update data
		studentEnqRepo.save(studentEntity);


		return true;
	}

}
