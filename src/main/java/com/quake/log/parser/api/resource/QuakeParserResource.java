package com.quake.log.parser.api.resource;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quake.log.parser.api.service.QuakeParserToMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
@Api(value = "quake parser")
public class QuakeParserResource {

	@Autowired
	private QuakeParserToMapService quakeParserToMapService;

	@GetMapping("/partidas/{id}")
	@ApiOperation(value = "Obter partida por Id")
	public ResponseEntity<?> getPartida(@PathVariable Long id) {
		Map<String, Object> partida = this.quakeParserToMapService.getPartida(id);
		return new ResponseEntity<Map<String, Object>>(partida, HttpStatus.OK);
	}

	@GetMapping("/partidas")
	@ApiOperation(value = "Obter todas as partidas")
	public ResponseEntity<?> partidas() {

		Map<String, Object> partida = this.quakeParserToMapService.getPartidas();
		return new ResponseEntity<Map<String, Object>>(partida, HttpStatus.OK);
	}
}
