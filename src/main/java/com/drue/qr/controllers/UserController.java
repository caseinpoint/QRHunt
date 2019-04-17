package com.drue.qr.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drue.qr.models.User;
import com.drue.qr.services.QRService;

@Controller
public class UserController {
	private final QRService service;
	
	public UserController(QRService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public String getIndex(HttpSession session,
			@ModelAttribute("user") User user) {
		
		session.setMaxInactiveInterval(0);
		return "index.jsp";
	}
	
	@PostMapping("/users")
	public String postUsers(@Valid @ModelAttribute("user") User user,
			BindingResult result,
			HttpSession session) {
		
		if (!user.getPassword().equals(user.getPasswordConfirmation()))
			result.addError(new FieldError("user", "passwordConfirmation", "Passwords must match"));
		if (result.hasErrors())
			return "index.jsp";
		else {
			User reg = service.registerUser(user);
			if (reg == null) {
				result.addError(new FieldError("user", "alias", "That alias is already taken"));
				return "index.jsp";
			}
			else {
				session.setAttribute("uId", reg.getId());
				session.setMaxInactiveInterval(0);
				String redirect = (String) session.getAttribute("redirect");
				if (redirect == null) redirect = "redirect:/games";
				return redirect;
			}
		}
	}
	
	@PostMapping("/users/login")
	public String postUsersLogin(@RequestParam("alias") String alias,
			@RequestParam("password") String password,
			HttpSession session,
			RedirectAttributes redAtt) {
		
		if (!service.authenticateUser(alias, password)) {
			redAtt.addFlashAttribute("msg", "Invalid login");
			return "redirect:/";
		}
		else {
			User log = service.findUserByAlias(alias);
			session.setAttribute("uId", log.getId());
			session.setMaxInactiveInterval(0);
			String redirect = (String) session.getAttribute("redirect");
			if (redirect == null) redirect = "redirect:/games";
			return redirect;
		}
	}
	
	@GetMapping("/users/logout")
	public String getUsersLogout(HttpSession session,
			RedirectAttributes redAtt) {
		
		session.removeAttribute("uId");
		session.removeAttribute("redirect");
		redAtt.addFlashAttribute("msg", "You have been logged out");
		return "redirect:/";
	}
	
	@GetMapping("/users/{id}")
	public String getUsersId(HttpSession session,
			@PathVariable("id") Long id,
			RedirectAttributes redAtt,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/users/" + id);
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			User user = service.findUserById(id);
			if (!user.getId().equals(uId)) return "redirect:/games";
			else {
				model.addAttribute("user", user);
				return "users_show.jsp";
			}
		}
	}
	
}
