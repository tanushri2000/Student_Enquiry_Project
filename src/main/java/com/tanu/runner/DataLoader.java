package com.tanu.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.tanu.entity.CourseNameEntity;
import com.tanu.entity.EnquiryStatusEntity;
import com.tanu.repo.CourseNameRepo;
import com.tanu.repo.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CourseNameRepo courseNameRepo;
	
	@Autowired
	private EnquiryStatusRepo enquiryStatusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseNameRepo.deleteAll();
		
		enquiryStatusRepo.deleteAll();
		
		CourseNameEntity course1= new CourseNameEntity();
		course1.setCourseName("Java FullStack");
		
		CourseNameEntity course2= new CourseNameEntity();
		course2.setCourseName("Python FullStack");
		
		CourseNameEntity course3= new CourseNameEntity();
		course3.setCourseName("DeVops");
		
		CourseNameEntity course4= new CourseNameEntity();
		course4.setCourseName("Testing");
		
		CourseNameEntity course5= new CourseNameEntity();
		course5.setCourseName("AWS");
		
		CourseNameEntity course6= new CourseNameEntity();
		course6.setCourseName("Oracle");
		
		List<CourseNameEntity> courseList = Arrays.asList(course1,course2,course3,course4,course5,course6);
		
		courseNameRepo.saveAll(courseList);
		
		
		EnquiryStatusEntity status1=new EnquiryStatusEntity();
		status1.setStatusName("New");
		

		EnquiryStatusEntity status2=new EnquiryStatusEntity();
		status2.setStatusName("Entrolled");
		

		EnquiryStatusEntity status3=new EnquiryStatusEntity();
		status3.setStatusName("Lost");
		
		List<EnquiryStatusEntity> statusList = Arrays.asList(status1,status2,status3);
		
		enquiryStatusRepo.saveAll(statusList);
		
	}

}
