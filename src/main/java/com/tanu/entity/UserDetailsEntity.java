package com.tanu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name="UserDetails_tbl")
@Data
public class UserDetailsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	private String email;
	private Long phone;
	private String password;
	private String accountStatus;
	
	@OneToMany(mappedBy="user" ,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JsonBackReference
	private List<StudentEnquiryEntity> studentEnquiry;
	

}
