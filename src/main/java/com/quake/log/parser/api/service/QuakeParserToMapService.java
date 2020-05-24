package com.quake.log.parser.api.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quake.log.parser.api.models.Kill;
import com.quake.log.parser.api.models.MotivoMorte;
import com.quake.log.parser.api.models.Partida;
import com.quake.log.parser.api.models.Player;

@Service
public class QuakeParserToMapService{

	@Autowired
	private IQuakeParser QuakeParser;

	public Map<String, Object> getPartida(Long id) {

		Map<String, Object> partidaMap = new LinkedHashMap<String, Object>();
		List<Partida> partida = QuakeParser.read().stream().filter(part -> part.getId() == id)
				.collect(Collectors.toList());

			 if(partida.size()  > 0) {
				 MontarPartida(partidaMap, partida);
			 }else {
				 partidaMap.put("error","Nao encontrado game com o id: "+ id);
			 }

		return partidaMap;
	}

	public Map<String, Object> getPartidas() {

		Map<String, Object> partidasMap = new LinkedHashMap<String, Object>();
		List<Partida> partidas = QuakeParser.read().stream().sorted(Comparator.comparing(Partida::getId))
				.collect(Collectors.toList());

		MontarPartida(partidasMap, partidas);

		return partidasMap;
	}

	private void MontarPartida(Map<String, Object> partidasMap, List<Partida> partidas) {

		partidas.forEach(partida -> {

			Map<String, Object> partidaMap = new LinkedHashMap<String, Object>();
			List<Player> players = partida.getPlayers().stream().filter(player -> !player.getNome().equals("<world>"))
					.collect(Collectors.toList());

			partidaMap.put("total_kills", partida.getKills().size());
			partidaMap.put("players", players.stream().map(player -> player.getNome()));

			partidaMap.put("kills", MontarKills(partida, players));
			partidaMap.put("kills_by_means", MontarKillsByMeans(partida));

			partidasMap.put("game_" + partida.getId(), partidaMap);
		});
	}

	private Map<String, Object> MontarKills(Partida partida, List<Player> listPlayers) {
		Map<String, Object> kills = new LinkedHashMap<String, Object>();

		listPlayers.forEach(player -> {

			Long qtdeMortesWorld = partida.getKills().stream()
					.filter(kill -> kill.getMatou().getNome().equals("<world>")
							&& kill.getMorreu().getNome().equals(player.getNome()))
					.count();

			Long killsPlayer = player.getKills().size() - qtdeMortesWorld;
			killsPlayer = killsPlayer < 0 ? 0 : killsPlayer;
			kills.put(player.getNome(), killsPlayer);
		});

		return kills;

	}

	private Map<MotivoMorte, Long> MontarKillsByMeans(Partida x) {
		Map<MotivoMorte, Long> killsByMeansMap = x.getKills().stream()
				.collect(Collectors.groupingBy(Kill::getMotivoMorte, Collectors.counting()));

		return killsByMeansMap;
	}
}
