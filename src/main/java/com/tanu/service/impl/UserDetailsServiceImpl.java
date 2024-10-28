package com.tanu.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanu.binding.LoginForm;
import com.tanu.binding.SignUpForm;
import com.tanu.binding.UnlockForm;
import com.tanu.entity.UserDetailsEntity;
import com.tanu.repo.UserDetailsRepo;
import com.tanu.service.UserDetailsService;
import com.tanu.utils.EmailUtils;
import com.tanu.utils.PasswordUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsRepo userRepo;

	@Autowired
	private PasswordUtils pass;

	@Autowired
	private EmailUtils emailSend;
	
	@Autowired
	private HttpSession session;

	@Override
	public boolean signUpForm(SignUpForm signupForm) {

		// TODO : check duplicate email
		UserDetailsEntity user = userRepo.findByEmail(signupForm.getEmail());
		if (user != null) {
			return false;
		}

		// TODO : copy binding obj to entity obj
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(signupForm, entity);

		// TODO : generate pwd and set
		String tempPwd = pass.passGenerator();
		entity.setPassword(tempPwd);

		// TODO : set Account status
		entity.setAccountStatus("LOCKED");

		// TODO : perform insert operation
		userRepo.save(entity);

		// TODO : send email to unlock account
		String to = signupForm.getEmail();

		String subject = "Unlock Your Account";

		StringBuffer body = new StringBuffer();
		body.append("<h1>Use below temporary password to unlock your account</h1>");
		body.append("<br/>");
		body.append("Temporary pwd :" + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your account</a>");

		emailSend.send(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockForm(UnlockForm unlockForm) {

		UserDetailsEntity entity = userRepo.findByEmail(unlockForm.getEmail());

		if (entity.getPassword().equals(unlockForm.getTempPassword())) {

			entity.setPassword(unlockForm.getNewPassword());
			entity.setAccountStatus("UNLOCKED");
			userRepo.save(entity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String loginForm(LoginForm loginForm) {
         
		//check record present or not based on email and password
		UserDetailsEntity entity = userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
		
		//if record is not available then return error msg
		if (entity == null) {
			return "Invalid credentials";
		}
		//if record is  available then check account status locked or unlocked if status is locked return error msg
		if (entity.getAccountStatus().equals("LOCKED")) {
			return "Your Account locked";
		}
		
		//if both conditions are false then create session object
		session.setAttribute("userId", entity.getUserId());
		
		return "success";
	}

	@Override
	public boolean forgotPwd(String email) {
		
		//check record present or not with give email
		UserDetailsEntity entity = userRepo.findByEmail(email);
		
		//if record not available in db return false
		if(entity==null) {
		return false;
	    }
		
		//if record available in db sent password to email and return true 
		 
		 String subject="Recover Forgot Password";
			 
		 StringBuffer body= new StringBuffer();
		 body.append("<h4>Use below Recover password to login your account</h4>");
	     body.append("<br/>");
		 body.append("Recover pwd :"+ entity.getPassword());
		 
		 emailSend.send(email, subject,body.toString() );
	     return true;
}

}