package com.tanu.utils;



import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
	
     public String passGenerator() {
    	 
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	String pwd = RandomStringUtils.random(6, characters);
	return pwd;

     }
}
