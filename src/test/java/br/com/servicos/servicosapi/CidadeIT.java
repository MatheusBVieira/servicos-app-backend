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

import br.com.servicos.servicosApi.domain.model.Cidade;
import br.com.servicos.servicosApi.domain.model.Estado;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.CidadeRepository;
import br.com.servicos.servicosApi.domain.repository.EstadoRepository;
import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;
import br.com.servicos.servicosApi.domain.util.UsuarioUtil;
import br.com.servicos.servicosapi.util.DatabaseCleaner;
import br.com.servicos.servicosapi.util.PopulaTestUtils;
import br.com.servicos.servicosapi.util.ResourceUtils;
import br.com.servicos.servicosapi.util.UsuarioTestUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CidadeIT {
	private static final int CIDADE_ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	private String token;
	private Usuario usuarioAdmin;
	private Cidade cidadeFloripa;
	private int quantidadeCidadesCadastradas;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cidades";

		databaseCleaner.clearTables();
		prepararDados();
		token = UsuarioTestUtil.pegaTokenAdmin();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCidade() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCidades_QuandoConsultarCidades() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeCidadesCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCidade() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/cidade-floripa-cadastra.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCidadeExistente() {
		given()
			.pathParam("cidadeId", cidadeFloripa.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cidadeFloripa.getNome()));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarCidade() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/cidade-floripa-atualiza.json"))
			.pathParam("cidadeId", cidadeFloripa.getId())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.put("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoDeletarCidade() {
		given()
			.pathParam("cidadeId", cidadeFloripa.getId())
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.delete("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCidadeInexistente() {
		given()
			.pathParam("cidadeId", CIDADE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		usuarioAdmin = UsuarioUtil.criaAdmin("12345678");
		usuarioRepository.save(usuarioAdmin);
		
		Estado estadoSC = PopulaTestUtils.criaEstadoSC();
		estadoRepository.save(estadoSC);
		
		cidadeFloripa = PopulaTestUtils.criaCidadeFloripa(estadoSC);
		cidadeRepository.save(cidadeFloripa);
		
		quantidadeCidadesCadastradas = (int) cidadeRepository.count();
	}
	
}
