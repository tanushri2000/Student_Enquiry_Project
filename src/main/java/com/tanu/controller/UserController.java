package com.tanu.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tanu.binding.LoginForm;
import com.tanu.binding.SignUpForm;
import com.tanu.binding.UnlockForm;
import com.tanu.service.UserDetailsService;

@Controller
public class UserController {

	@Autowired
	private UserDetailsService userService;
	

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("signupForm", new SignUpForm());
		return "signup";

	}

	@PostMapping("/signup")
	public String signupHandler(@ModelAttribute("signupForm") SignUpForm signupForm, Model model) {
		boolean status = userService.signUpForm(signupForm);
		if (status) {
			model.addAttribute("succesMsg", "Account created,check your email");
		} else {
			model.addAttribute("errorMsg", "Provide valid email");
		}
		return "signup";

	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlock", unlockForm);
		return "unlock";

	}

	@PostMapping("/unlock")
	public String unlockHandler(@ModelAttribute("unlock") UnlockForm unlockForm, Model model) {

		if (unlockForm.getNewPassword().equals(unlockForm.getConfirmPassword())) {
			boolean status = userService.unlockForm(unlockForm);
			if (status) {
				model.addAttribute("successMsg", "Unlock Account Successfully");
			} else {
				model.addAttribute("errorMsg", "Invalid temparary password,check your email");
			}

		} else {
			model.addAttribute("errorMsg", "New Password And Confirm Password should be same");
		}

		return "unlock";

	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";

	}

	
	@PostMapping("/login")
	public String loginHandler(@ModelAttribute("login") LoginForm loginForm,Model model) {
		
		String status = userService.loginForm(loginForm);
		if(status.contains("success")) {
			return "redirect:/dashboard";
		}
		model.addAttribute("errorMsg", status);
		return "login";

	}

	
	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";

	}
	@PostMapping("/forgotPwd")
	public String forgotPwdPage(@RequestParam("email") String email,Model model) {
		
		boolean status = userService.forgotPwd(email);
		if(status) {
			//sent success msg
			model.addAttribute("successMsg", "Password sent to your Email");
		}else {
			// sent error msg
			model.addAttribute("errorMsg", "Invalid Email");
		}
		return "forgotPwd";

	}
	

}
