package com.drue.qr.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drue.qr.models.Game;
import com.drue.qr.models.Hint;
import com.drue.qr.models.User;
import com.drue.qr.services.QRService;

@Controller
@RequestMapping("/games")
public class GameController {
	private final QRService service;
	
	public GameController(QRService service) {
		this.service = service;
	}
	
	@GetMapping("")
	public String getGames(HttpSession session,
			Model model,
			RedirectAttributes redAtt) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games");
			return "redirect:/";
		}
		else {
			session.removeAttribute("redirect");
			model.addAttribute("user", service.findUserById(uId));
			return "dashboard.jsp";
		}
	}
	
	@GetMapping("/new")
	public String getGamesNew(HttpSession session,
			@ModelAttribute("game") Game game,
			RedirectAttributes redAtt) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/new");
			return "redirect:/";
		}
		else {
			session.removeAttribute("redirect");
			return "games_new.jsp";
		}
	}
	
	@PostMapping("")
	public String postGames(HttpSession session,
			@Valid @ModelAttribute("game") Game game,
			BindingResult result,
			RedirectAttributes redAtt) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games");
			return "redirect:/";
		}
		session.removeAttribute("redirect");
		if (result.hasErrors()) return "games_new.jsp";
		else {
			User user = service.findUserById(uId);
			game.setCreator(user);
			game = service.saveGame(game);
			return "redirect:/games/" + game.getId() + "/hint";
		}
	}
	
	@GetMapping("/{id}/hint")
	public String getGamesIdHint(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/hint");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			Game game = service.findGameById(gId);
			if (!game.getCreator().getId().equals(uId)) return "redirect:/games";
			
			model.addAttribute("game", game);
			Hint hint = new Hint();
			hint.setGame(game);
			model.addAttribute("hint", hint);
			return "hints_new.jsp";
		}
	}
	
	@PostMapping("/{id}/hint")
	public String postGamesIdHint(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId,
			@Valid @ModelAttribute("hint") Hint hint,
			BindingResult result) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/hint");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			if (result.hasErrors()) return "hints_new.jsp";
			service.saveHint(hint);
			return "redirect:/games/" + gId + "/manage";
		}
	}
	
	@GetMapping("/{gId}/hint/{hId}")
	public String getGamesIdHintId(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("gId") Long gId,
			@PathVariable("hId") Long hId,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/hint/" + hId);
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			Hint hint = service.findHintById(hId);
			if (!hint.getGame().getCreator().getId().equals(uId)) return "redirect:/games";
			model.addAttribute("hint", hint);
			return "hints_edit.jsp";
		}
	}
	
	@PostMapping("/{gId}/hint/{hId}")
	public String postGamesIdHintId(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("gId") Long gId,
			@PathVariable("hId") Long hId,
			@Valid @ModelAttribute("hint") Hint hint,
			BindingResult result) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/hint/" + hId);
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			if (!hint.getGame().getCreator().getId().equals(uId)) return "redirect:/games";
			if (result.hasErrors()) return "hints_edit.jsp";
			service.saveHint(hint);
			return "redirect:/games/" + gId + "/manage";
		}
	}
	
	@GetMapping("/{gId}/hint/{hId}/delete")
	public String getGamesIdHintIdDelete(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("gId") Long gId,
			@PathVariable("hId") Long hId) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/hint/" + hId + "/delete");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			Hint hint = service.findHintById(hId);
			if (!hint.getGame().getCreator().getId().equals(uId)) return "redirect:/games";
			service.deleteHint(hint);
			return "redirect:/games/" + gId + "/manage";
		}
	}
	
	@GetMapping("/available")
	public String getGamesAvailable(HttpSession session,
			RedirectAttributes redAtt,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			model.addAttribute("available", service.findActiveGames());
		} else {
			model.addAttribute("available", service.findAvailableGames(uId));
		}
		return "games_available.jsp";
	}
	
	@GetMapping("/{id}/join")
	public String getGamesIdJoin(HttpSession session,
			@PathVariable("id") Long gId,
			RedirectAttributes redAtt) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/join");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			if (service.joinGame(gId, uId) != null)
				return "redirect:/games/" + gId + "/view";
			else return "redirect:/games";
		}
	}
	
	@GetMapping("/{id}/leave")
	public String getGamesIdLeave(HttpSession session,
			@PathVariable("id") Long gId,
			RedirectAttributes redAtt) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/join");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			service.leaveGame(gId, uId);
			return "redirect:/games";
		}
	}
	
	@GetMapping("/{id}")
	public String getGamesId(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) uId = -1L;
		Game game = service.findGameById(gId);
		if (game.getCreator().getId().equals(uId))
			return "redirect:/games/" + gId + "/manage";
		else return "redirect:/games/" + gId + "/view";
	}
	
	@GetMapping("/{id}/view")
	public String getGamesIdView(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) uId = -1L;
		Game game = service.findGameById(gId);
		User user = service.findUserById(uId);
		if (game.getCreator().equals(user))
			return "redirect:/games/" + gId + "/manage";
		model.addAttribute("game", game);
		model.addAttribute("user", user);
		if (game.getPlayers().contains(user))
			return "games_show_player.jsp";
		else return "games_show_potential.jsp";
	}
	
	@GetMapping("/{id}/manage")
	public String getGamesIdManage(HttpSession session,
			@PathVariable("id") Long gId,
			RedirectAttributes redAtt,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/manage");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			Game game = service.findGameById(gId);
			if (!game.getCreator().getId().equals(uId))
				return "redirect:/games/" + gId + "/view";
			else {
				model.addAttribute("game", game);
				return "games_show_creator.jsp";
			}
		}
	}
	
	@GetMapping("/{id}/delete")
	public String getGamesIdDelete(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/delete");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			Game game = service.findGameById(gId);
			if (!game.getCreator().getId().equals(uId))
				return "redirect:/games";
			service.deleteGame(game);
			return "redirect:/users/" + uId;
		}
	}
	
	@GetMapping("/{id}/edit")
	public String getGamesIdEdit(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId,
			Model model) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/edit");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			Game game = service.findGameById(gId);
			if (!game.getCreator().getId().equals(uId))
				return "redirect:/games";
			model.addAttribute("game", game);
			return "games_edit.jsp";
		}
	}
	
	@PostMapping("/{id}")
	public String postGamesId(HttpSession session,
			RedirectAttributes redAtt,
			@PathVariable("id") Long gId,
			@Valid @ModelAttribute("game") Game game,
			BindingResult result) {
		
		Long uId = (Long) session.getAttribute("uId");
		if (uId == null) {
			redAtt.addFlashAttribute("msg", "You must login first");
			session.setAttribute("redirect", "redirect:/games/" + gId + "/edit");
			return "redirect:/";
		} else {
			session.removeAttribute("redirect");
			if (!game.getCreator().getId().equals(uId))
				return "redirect:/games";
			if (result.hasErrors()) return "games_edit.jsp";
			service.saveGame(game);
			return "redirect:/games/" + gId + "/manage";
		}
	}
	
}
