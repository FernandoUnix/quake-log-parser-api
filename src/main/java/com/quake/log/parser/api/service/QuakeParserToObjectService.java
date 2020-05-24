package com.quake.log.parser.api.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.quake.log.parser.api.models.Kill;
import com.quake.log.parser.api.models.MotivoMorte;
import com.quake.log.parser.api.models.Partida;
import com.quake.log.parser.api.models.Player;
import com.quake.log.parser.api.util.ParserUtil;

@Service
public class QuakeParserToObjectService implements IQuakeParser {

	@Value("${file.log.path}")
	private String urlPathFile;

	@Value("${game.inicio}")
	private String inicioGame;

	private static Logger log = LoggerFactory.getLogger(QuakeParserToObjectService.class);

	public List<Partida> read() {

		URI uri;
		List<String> lines;
		try {

			uri = this.getClass().getResource(urlPathFile).toURI();
			lines = Files.readAllLines(Paths.get(uri));
			List<List<String>> partidas = ParserUtil.splitBySeparator(lines, x -> x.contains(inicioGame));

			partidas.remove(0);

			log.info("Quantidade de partidas : " + partidas.size());

			List<Partida> lstPartidas = new ArrayList<Partida>();

			partidas.forEach(part -> {

				Partida partida = new Partida();
				partida.setId(Long.valueOf(lstPartidas.size() + 1));

				List<String> jogadores = part.stream().filter(x -> x.contains("ClientUserinfoChanged"))
						.collect(Collectors.toList());

				if (jogadores.size() > 0) {
					MontarPlayers(partida, jogadores);
				}

				if (part.stream().filter(x -> x.contains("<world>")).findFirst() != null) {
					Player p = new Player();
					p.setNome("<world>");
					partida.addPlayer(p);
				}

				List<String> kills = part.stream().filter(x -> x.contains("killed")).collect(Collectors.toList());

				if (kills.size() > 0) {
					MontarKills(partida, kills);
				}

				lstPartidas.add(partida);

			});

			return lstPartidas;

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void MontarKills(Partida partida, List<String> kills) {
		kills.forEach(kill -> {
			Kill k = MontarKill(partida, kill);
			partida.addKill(k);

		});
	}

	private Kill MontarKill(Partida partida, String kill) {

		Kill k = new Kill();

		int indexOfKilled = kill.indexOf("killed");
		int indexOfBy = kill.indexOf("by");

		String matou = kill.substring(kill.lastIndexOf(":") + 2, indexOfKilled).trim();
		String morreu = kill.substring(indexOfKilled + 7, indexOfBy).trim();
		String motivo = kill.substring(indexOfBy + 3).trim();

		Player playerMatou = partida.getPlayerByNome(matou);

		k.setMatou(playerMatou);
		k.setMorreu(partida.getPlayerByNome(morreu));
		k.setMotivoMorte(ParserUtil.getEnumFromString(MotivoMorte.class, motivo));

		playerMatou.addKill(k);

		return k;
	}

	private void MontarPlayers(Partida partida, List<String> jogadores) {

		jogadores.forEach(x -> {
			Player player = MontarPlayer(x);
			partida.addPlayer(player);
		});
	}

	private Player MontarPlayer(String x) {

		Player player = new Player();

		int indexInicioNomeJogador = x.indexOf("n\\") + 2;
		int indexFimNomeJogador = x.indexOf("\\t\\");
		String nomeJogador = x.substring(indexInicioNomeJogador, indexFimNomeJogador);

		player.setNome(nomeJogador);

		return player;
	}
}
