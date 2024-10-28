package com.tanu.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="StudentEnquiry_tbl")
@Data
public class StudentEnquiryEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer enquiryId;
	private String studentName;
	private Long phone;
	private String classMode;
	private String courseName;
	private String enqStatus;
	@Column(name="create_date",updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	@UpdateTimestamp
	private LocalDate updateDate;
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonManagedReference
    private UserDetailsEntity user;
	

}
