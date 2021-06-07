package br.com.servicos.servicosapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.servicos.servicosApi.domain.model.Estado;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.EstadoRepository;
import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;
import br.com.servicos.servicosApi.domain.util.UsuarioUtil;
import br.com.servicos.servicosapi.util.DatabaseCleaner;
import br.com.servicos.servicosapi.util.ResourceUtils;
import br.com.servicos.servicosapi.util.UsuarioTestUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class EstadoIT {
	private static final int ESTADO_ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private int quantidadeEstadosCadastrados;
	private String token;
	private Usuario usuarioAdmin;
	private Estado estadoSP;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/estados";

		databaseCleaner.clearTables();
		prepararDados();
		token = UsuarioTestUtil.pegaTokenAdmin();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarEstados() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeEstados_QuandoConsultarEstados() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeEstadosCadastrados));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarEstado() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/estado-sc-cadastra.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarEstadoExistente() {
		given()
			.pathParam("estadoId", estadoSP.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{estadoId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(estadoSP.getNome()));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarEstado() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/estado-sc-atualiza.json"))
			.pathParam("estadoId", estadoSP.getId())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.put("/{estadoId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoDeletarEstado() {
		given()
			.pathParam("estadoId", estadoSP.getId())
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.delete("/{estadoId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarEstadoInexistente() {
		given()
			.pathParam("categoriaId", ESTADO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{categoriaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		usuarioAdmin = UsuarioUtil.criaAdmin("12345678");
		usuarioRepository.save(usuarioAdmin);
		
		estadoSP = new Estado();
		estadoSP.setNome("São Paulo");
		estadoRepository.save(estadoSP);
		
		quantidadeEstadosCadastrados = (int) estadoRepository.count();
	}
}
