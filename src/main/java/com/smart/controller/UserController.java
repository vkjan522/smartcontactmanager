package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// Method for adding common data resopnse
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String name = principal.getName();
		// System.out.println("UserName: "+name);
		// get the user details using username(Email)
		User user = userRepository.getUserByUserName(name);
		// System.out.println("User: "+user);
		model.addAttribute("user", user);
	}

	// DashBoard Home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "Home");
		return "normal/user_dashboard";
	}

	// Open Add contact form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	@GetMapping("/view-contacts")
	public String viewContacts(Model model) {
		model.addAttribute("title", "View Contact");
		return "normal/view_contacts";
	}

	@GetMapping("/user-profile")
	public String userProfile(Model model) {
		model.addAttribute("title", "User Profile");
		return "normal/user_profile";
	}

	@GetMapping("/user-setting")
	public String userSetting(Model model) {
		model.addAttribute("title", "User Setting");
		return "normal/user_setting";
	}

	// Process add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, Model model, Principal principal) {
		model.addAttribute("title", "Add Contact");
		String name = principal.getName();
		User userByUserName = this.userRepository.getUserByUserName(name);
		contact.setUser(userByUserName);
		userByUserName.getContacts().add(contact);
		this.userRepository.save(userByUserName);
		System.out.println("DATA" + contact);
		System.out.println("Added to database");
		return "normal/add_contact_form";
	}

}
