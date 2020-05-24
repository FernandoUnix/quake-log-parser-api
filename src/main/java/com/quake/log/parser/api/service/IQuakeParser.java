package com.quake.log.parser.api.service;

import java.util.List;
import com.quake.log.parser.api.models.Partida;

public interface IQuakeParser {
	List<Partida> read();
}
