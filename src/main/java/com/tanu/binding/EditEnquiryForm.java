package com.tanu.binding;

import lombok.Data;

@Data
public class EditEnquiryForm {
	
    private Integer enquiryId;
	private String studentName;
	private Long Phone;
	private String classMode;
	private String courseName;
	private String enqStatus;
}
