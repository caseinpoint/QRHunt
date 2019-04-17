package com.drue.qr.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="games")
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Size(min=1, message="Game title is required")
	private String title;
	@Size(min=1, max= 174, message="QR text must be between 1 and 174 characters")
	private String qrText;
	private Integer winnerLimit;
	private boolean active;
	@Size(min=1, message="Win message is required")
	private String winMessage;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User creator;
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    	name="games_players",
    	joinColumns=@JoinColumn(name="game_id"),
    	inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> players;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    	name="games_winners",
    	joinColumns=@JoinColumn(name="game_id"),
    	inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> winners;
    
    @OneToMany(mappedBy="game", fetch=FetchType.LAZY)
    private List<Hint> hints;
    
    public Game() {
    	winnerLimit = 0;
    	active = true;
    }
    
    @PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQrText() {
		return qrText;
	}

	public void setQrText(String qrText) {
		this.qrText = qrText;
	}

	public Integer getWinnerLimit() {
		return winnerLimit;
	}

	public void setWinnerLimit(Integer numWinners) {
		this.winnerLimit = numWinners;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getWinMessage() {
		return winMessage;
	}

	public void setWinMessage(String winMessage) {
		this.winMessage = winMessage;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<User> getPlayers() {
		return players;
	}
	
	public int getPlayersCount() {
		return players.size();
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public List<User> getWinners() {
		return winners;
	}
	
	public int getWinnersCount() {
		return winners.size();
	}

	public void setWinners(List<User> winners) {
		this.winners = winners;
		if (winnerLimit > 0 && this.winners.size() >= winnerLimit)
			active = false;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public List<Hint> getHints() {
		return hints;
	}
	
	public int getHintsCount() {
		return hints.size();
	}
	public void setHints(List<Hint> hints) {
		this.hints = hints;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Game)) return false;
		Game other = (Game) obj;
		if (other.id != this.id) return false;
		else return true;
	}
	
}
