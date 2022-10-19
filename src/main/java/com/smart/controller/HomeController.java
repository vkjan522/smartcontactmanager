package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userrepository;

	@RequestMapping("/newhome")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact manager");
		return "home";
	}

	@RequestMapping("/newnamehome")
	public String nameHome(Model model) {
		model.addAttribute("title", "Home - Smart Contact manager");
		return "home";
	}

	@RequestMapping("/newabout")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact manager");
		return "about";
	}

	@RequestMapping("/newsignup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - Smart Contact manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping("/newlogin")
	public String login(Model model) {
		model.addAttribute("title", "Login - Smart Contact manager");
		return "login";
	}

	// this handler for registering user.....
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {
		try {
			if (!agreement) {
				System.out.println("You have not agreed terms and conditions");
				throw new Exception("You have not agreed terms and conditions");
			}
			if (result1.hasErrors()) {
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			System.out.println("Agreement " + agreement);
			System.out.println("User: " + user);
			User result = this.userrepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!!" + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}

}
