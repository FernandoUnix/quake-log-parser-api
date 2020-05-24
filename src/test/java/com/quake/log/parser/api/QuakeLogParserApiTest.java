package com.quake.log.parser.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuakeLogParserApiTest {

	@LocalServerPort
	private int port;

	public final String URL_BASE = "/api/v1/partidas";

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	public void deveRetornar200ConsultaPartidas() {
		given().port(port).log().all().when().get(URL_BASE).then().log().all().statusCode(200).extract().response();
	}

	@Test
	public void deveRetornar200ConsultaPartida1() {
		given().port(port).pathParam("id", 1).log().all().when().get(URL_BASE + "/{id}").then().log().all()
				.statusCode(200).extract().response();
	}

	@Test
	public void deveRetornarPartidaIdEnviado() {

		long id = 1;

		given().port(port).pathParam("id", id).log().all().when().get(URL_BASE + "/{id}").then().log().all()
				.statusCode(200).body(containsString("game_" + id));
	}

	@Test
	public void deveRetornarMensagemPartidaNaoEncontrado() {

		long id = 999;

		given().port(port).pathParam("id", id).log().all().when().get(URL_BASE + "/{id}").then().log().all()
				.statusCode(200).body("error", containsString("Nao encontrado game com o id: " + id));
	}
}
