package com.quake.log.parser.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Kill {

	@JsonIgnoreProperties(value = {"kills"}, allowSetters= true)
	private Player matou;
	
	@JsonIgnoreProperties(value = {"kills"}, allowSetters= true)
	private Player morreu;

	private MotivoMorte motivoMorte;

	public Player getMatou() {
		return matou;
	}

	public void setMatou(Player matou) {
		this.matou = matou;
	}

	public Player getMorreu() {
		return morreu;
	}

	public void setMorreu(Player morreu) {
		this.morreu = morreu;
	}

	public Player getPlayer() {
		return matou;
	}

	public void setPlayer(Player player) {
		this.matou = player;
	}

	public MotivoMorte getMotivoMorte() {
		return motivoMorte;
	}

	public void setMotivoMorte(MotivoMorte motivoMorte) {
		this.motivoMorte = motivoMorte;
	}
}
