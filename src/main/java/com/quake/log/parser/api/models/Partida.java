package com.quake.log.parser.api.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Partida {

	private Long id;
	private Integer totalMortes;
	private Set<Player> players;
	private List<Kill> kills;

	public Partida() {
		this.players = new HashSet<Player>();
		this.kills = new ArrayList<Kill>();
	}

	public void addKill(Kill kill) {
		kills.add(kill);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public List<Kill> getKills() {
		return kills;
	}

	public Player getPlayerByNome(String nome) {
		Player player = this.players.stream().filter(x -> x.getNome().equals(nome)).findFirst().orElse(null);
		return player;
	}

	public void setKills(List<Kill> kills) {
		this.kills = kills;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalMortes() {
		totalMortes = kills.size();
		return totalMortes;
	}

	public Set<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
}
