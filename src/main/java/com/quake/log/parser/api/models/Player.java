package com.quake.log.parser.api.models;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String nome;
	private List<Kill> kills = new ArrayList<Kill>();

	public void addKill(Kill kill) {
		kills.add(kill);
	}
	
	@Override
	public String toString() {
		return  nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		String nomeObj = ((Player)obj).getNome();
		return this.nome.equals(nomeObj);
	}
	
	@Override
	public int hashCode() {
	    return nome.hashCode();
	}
	
	public List<Kill> getKills() {
		return kills;
	}

	public void setKills(List<Kill> kills) {
		this.kills = kills;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
