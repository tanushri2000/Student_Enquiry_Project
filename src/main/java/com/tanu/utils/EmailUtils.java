package com.tanu.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean send(String to, String subject,String body) { 
		
		boolean isSent=false;
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper= new MimeMessageHelper(mimeMessage);
		    helper.setTo(to);
			helper.setText(body, true);
			helper.setSubject(subject);
			
			mailSender.send(mimeMessage);
		 
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return isSent;
	
	}

}
