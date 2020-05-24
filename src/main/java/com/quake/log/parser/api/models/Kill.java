package com.quake.log.parser.api.models;

public class Kill {

	private Player matou;

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
