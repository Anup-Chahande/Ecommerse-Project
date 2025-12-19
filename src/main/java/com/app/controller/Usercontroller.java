package com.app.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.service.Userservice;
import com.app.user.User;

@Controller
public class Usercontroller {

	@Autowired
	Userservice us;

	@GetMapping("/login")
	public String loginpage() {

		return "Login";
	}

	@GetMapping("/regi")
	public String registration() {

		return "Registration";
	}

	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "passreset";
	}

	@PostMapping("/adduser")
	public String adduser(@RequestParam("name") String name, @RequestParam("number") String number,
			@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes ra) {

		User u = new User();
		u.setName(name);
		u.setNumber(number);
		u.setEmail(email);
		u.setPassword(password);

		us.adduser(u);

		ra.addFlashAttribute("msg", "Registration Successful! Please login.");

		return "redirect:/login";

	}

	@PostMapping("/logincheck")
	public String validatelogin(@RequestParam("eiduid") String eiduid, @RequestParam("password") String password,
			Model model,
			HttpSession session
			) {

		List<User> ls = us.getalluser();
		for (User user : ls) {

			if (eiduid.equals(user.getNumber()) && password.equals(user.getPassword())) {
			
				session.setAttribute("loggedUser", user);

				model.addAttribute("msg2", ls);

				
				return "redirect:/getallproducts";

			}

			else if (eiduid.equals(user.getEmail()) && password.equals(user.getPassword())) {
				
				
				session.setAttribute("loggedUser", user);

				
				return "redirect:/getallproducts";
			}
			

		}


		model.addAttribute("msg2", "Invalid number or password!");
		return "Login";

	}
	
	
	int otp=0;

	@GetMapping("/genrateotp")
	@ResponseBody
	public String genreteotp(@RequestParam("email") String email) {

		List<User> ls = us.getalluser();
		for (User user : ls) {

			if (user.getEmail().equals(email)) {

				Random ran = new Random();
				int otp = 1000 + ran.nextInt(9000);
                this.otp=otp;
				return String.valueOf(otp);

			}

		}

		return "Email not found";

	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); 
	    return "redirect:/login"; 
	}
	
	@GetMapping("/manageaccount/{id}")
	public String updateautofill(@PathVariable long id,Model model) {
		User u = us.getbyid(id);
		model.addAttribute("udata",u);
		
		return "manageproducts.html";
		
		
		
	}
	
	
	       
	      @PostMapping("/mngupdate")
	      public String  manageupdateaccount( @RequestParam("id") long id,@RequestParam("name") String name, @RequestParam("number") String number,
	       @RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes ra) {
	    	  User u = us.getbyid(id);
	    	  u.setName(name);
	    	  u.setNumber(number);
	    	  u.setEmail(email);
	    	  u.setPassword(password);

	    	  us.update(u);
			return "redirect:/getallproducts";

	  		
	        	   
	           }
	
	
	
	@PostMapping("/resetpassword")
	public String resetpassword(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("otp") String otpInput,Model model) {
		try {
	        int otpInt = Integer.parseInt(otpInput);
	        if(otpInt == this.otp) {
	            List<User> users = us.getalluser();
	            for(User user: users) {
	                if(user.getEmail().equals(email)) {
	                    user.setPassword(password);
	                    us.update(user); // <-- use update method here
	                    this.otp = 0;    // reset OTP
	                    model.addAttribute("msg", "Password updated successfully!");
	                    return "passreset";
	                }
	            }
	            model.addAttribute("msg", "Email not found!");
	        } else {
	            model.addAttribute("msg", "Invalid OTP!");
	        }
	    } catch(NumberFormatException e) {
	        model.addAttribute("msg", "OTP must be a number!");
	    }
	    return "passreset";
	}
	
	
	
	      
	
	}

	
