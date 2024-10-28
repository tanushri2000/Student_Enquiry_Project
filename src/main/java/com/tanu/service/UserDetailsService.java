package com.tanu.service;

import com.tanu.binding.LoginForm;
import com.tanu.binding.SignUpForm;
import com.tanu.binding.UnlockForm;

public interface UserDetailsService {
	
	
	
	public boolean signUpForm(SignUpForm signupForm);
	
	public boolean unlockForm(UnlockForm unlockForm);
	
	public String loginForm(LoginForm loginForm);
	
	public boolean forgotPwd(String email);

}
