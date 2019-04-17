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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
//import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
//	private boolean admin = false;
	@Size(min=1, max=63, message="Alias must be between 1 and 63 characters")
	@Column(unique=true)
	private String alias;
//	@Email(message="Invalid email")
//	@Column(unique=true)
//	private String email;
	@Size(min=8, message="Password must be at least 8 characters")
	private String password;
	@Transient
	private String passwordConfirmation;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
    private List<Game> gamesCreated;
    
//    @OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
//    private List<Hint> hintsCreated;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    	name="games_players",
    	joinColumns=@JoinColumn(name="user_id"),
    	inverseJoinColumns=@JoinColumn(name="game_id")
    )
    private List<Game> gamesJoined;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    	name="games_winners",
    	joinColumns=@JoinColumn(name="user_id"),
    	inverseJoinColumns=@JoinColumn(name="game_id")
    )
    private List<Game> gamesWon;
    
    public User() {}
    
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

//	public boolean isAdmin() {
//		return admin;
//	}
//
//	public void setAdmin(boolean admin) {
//		this.admin = admin;
//	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias.replaceAll("\\s", "");
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public List<Game> getGamesCreated() {
		return gamesCreated;
	}
	
	public int getGamesCreatedCount() {
		return gamesCreated.size();
	}

	public void setGamesCreated(List<Game> createdGames) {
		this.gamesCreated = createdGames;
	}

	public List<Game> getGamesJoined() {
		return gamesJoined;
	}
	
	public int getGamesJoinedCount() {
		return gamesJoined.size();
	}

	public void setGamesJoined(List<Game> joinedGames) {
		this.gamesJoined = joinedGames;
	}

	public List<Game> getGamesWon() {
		return gamesWon;
	}
	
	public int getGamesWonCount() {
		return gamesWon.size();
	}

	public void setGamesWon(List<Game> wonGames) {
		this.gamesWon = wonGames;
	}
	
}
