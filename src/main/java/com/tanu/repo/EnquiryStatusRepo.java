package com.tanu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.tanu.entity.EnquiryStatusEntity;

public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatusEntity,Integer>{

	
	 @Query("select statusName from EnquiryStatusEntity") 
	  public List<String> getEnquiryStatus();
}
