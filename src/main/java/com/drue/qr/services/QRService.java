package com.drue.qr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.drue.qr.models.Game;
import com.drue.qr.models.Hint;
import com.drue.qr.models.User;
import com.drue.qr.repositories.GameRepository;
import com.drue.qr.repositories.HintRepository;
import com.drue.qr.repositories.UserRepository;

@Service
public class QRService {
	private final UserRepository uRepo;
	private final GameRepository gRepo;
	private final HintRepository hRepo;
	
	public QRService(UserRepository uRepo, GameRepository gRepo, HintRepository hRepo) {
		this.uRepo = uRepo;
		this.gRepo = gRepo;
		this.hRepo = hRepo;
	}
	
	// User methods
	
	public List<User> findAllUsers() {
		return uRepo.findAll();
	}
	
	public User findUserById(Long id) {
		Optional<User> opt = uRepo.findById(id);
		if (opt.isPresent()) return opt.get();
		else return null;
	}
	
	public User findUserByAlias(String alias) {
		return uRepo.findByAlias(alias);
	}
	
	public User registerUser(User user) {
		if (uRepo.findByAlias(user.getAlias()) != null) return null;
		else {
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(hashed);
			return uRepo.save(user);
		}
	}
	
	public boolean authenticateUser(String alias, String password) {
		User user = uRepo.findByAlias(alias);
		if (user == null) return false;
		else {
			if (!BCrypt.checkpw(password, user.getPassword())) return false;
			else return true;
		}
	}
	
	// Game methods
	
	public List<Game> findAllGames() {
		return gRepo.findAll();
	}
	
	public Game findGameById(Long id) {
		Optional<Game> opt = gRepo.findById(id);
		if (opt.isPresent()) return opt.get();
		else return null;
	}
	
	public List<Game> findAvailableGames(Long userId) {
		User user = findUserById(userId);
		if (user == null) return null;
		
		List<Game> allGames = gRepo.findAll();
		if (allGames == null) return null;
		
		ArrayList<Game> available = new ArrayList<>();
		for (Game game : allGames) {
			if (game.isActive() &&
					!game.getCreator().equals(user) &&
					!game.getPlayers().contains(user) &&
					!game.getWinners().contains(user)) {
				
				available.add(game);
			}
		}
		return available;
	}
	
	public List<Game> findActiveGames() {
		return gRepo.findByActive(true);
	}
	
	public Game saveGame(Game game) {
		return gRepo.save(game);
	}
	
	public void deleteGame(Game game) {
		gRepo.delete(game);
	}
	
	public Game joinGame(Long gameId, Long userId) {
		Game game = findGameById(gameId);
		if (game == null) return null;
		
		User user = findUserById(userId);
		if (user == null) return null;
		
		if (game.getPlayers().contains(user) || game.getWinners().contains(user))
			return null;
		
		game.getPlayers().add(user);
		return gRepo.save(game);
	}
	
	public boolean leaveGame(Long gameId, Long userId) {
		Game game = findGameById(gameId);
		if (game == null) return false;
		
		User user = findUserById(userId);
		if (user == null) return false;
		
		game.getPlayers().remove(user);
		gRepo.save(game);
		return true;
	}
	
	public String playGame(Long game_id, Long user_id, String qrResult) {
		Optional<Game> gameOpt = gRepo.findById(game_id);
		if (!gameOpt.isPresent()) return "__error__";
		else {
			Optional<User> userOpt = uRepo.findById(user_id);
			if (!userOpt.isPresent()) return "__error__";
			else {
				Game game = gameOpt.get();
				if (!game.getQrText().equals(qrResult)) {
					return "__wrong__";
				} else {
					User user = userOpt.get();
					game.getPlayers().remove(user);
					game.getWinners().add(user);
					if (game.getWinnerLimit() != 0 && game.getWinnersCount() == game.getWinnerLimit()) {
						game.setActive(false);
					}
					gRepo.save(game);
					return game.getWinMessage();
				}
				
			}
		}
	}
	
	// Hint methods
	
	public List<Hint> findAllHints() {
		return hRepo.findAll();
	}
	
	public Hint findHintById(Long id) {
		Optional<Hint> opt = hRepo.findById(id);
		if (opt.isPresent()) return opt.get();
		else return null;
	}
	
	public Hint saveHint(Hint hint) {
		return hRepo.save(hint);
	}
	
	public void deleteHint(Hint hint) {
		hRepo.delete(hint);
	}
	
}
