package com.tanu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tanu.entity.CourseNameEntity;

public interface CourseNameRepo extends JpaRepository<CourseNameEntity, Integer> {

	
	  @Query("select courseName from CourseNameEntity") 
	  public List<String> getCourseName();
	  
	 
}
