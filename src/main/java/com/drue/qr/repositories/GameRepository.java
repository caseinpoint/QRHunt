package com.drue.qr.repositories;

import java.util.List;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.drue.qr.models.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
	List<Game> findAll();
	List<Game> findByActive(boolean bool);
	
//	@Query(value="SELECT games.* FROM games " +
//			"LEFT JOIN games_players " +
//			"ON games_players.game_id = games.id " +
//			"LEFT JOIN games_winners " +
//			"ON games_winners.game_id = games.id " +
//			"WHERE games.user_id != ?1 " +
//			"OR games_players.user_id != ?1 " +
//			"OR games_winners.user_id != ?1",
//			nativeQuery=true)
//	List<Game> findAvailableGames(Long userId);
}
