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

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.CategoriaRepository;
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
public class CategoriaIT {
	private static final int CATEGORIA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	private Categoria categoriaLimpeza;
	private int quantidadeCategoriasCadastradas;
	private Usuario usuarioAdmin;
	private String token;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/categorias";

		databaseCleaner.clearTables();
		prepararDados();
		token = UsuarioTestUtil.pegaTokenAdmin();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCategorias() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCategorias_QuandoConsultarCategorias() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeCategoriasCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCategoria() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/categoria-limpeza-cadastra.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCategoriaExistente() {
		given()
			.pathParam("categoriaId", categoriaLimpeza.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{categoriaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("categoria", equalTo(categoriaLimpeza.getCategoria()));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarCategoria() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/categoria-limpeza-atualiza.json"))
			.pathParam("categoriaId", categoriaLimpeza.getId())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.put("/{categoriaId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoDeletarCategoria() {
		given()
			.pathParam("categoriaId", categoriaLimpeza.getId())
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.delete("/{categoriaId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCategoriaInexistente() {
		given()
			.pathParam("categoriaId", CATEGORIA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{categoriaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		usuarioAdmin = UsuarioUtil.criaAdmin("12345678");
		usuarioRepository.save(usuarioAdmin);
		
		Categoria categoriaCaseiro = new Categoria();
		categoriaCaseiro.setCategoria("Caseiro");
		categoriaRepository.save(categoriaCaseiro);
		
		categoriaLimpeza = new Categoria();
		categoriaLimpeza.setCategoria("Limpeza");
		categoriaRepository.save(categoriaLimpeza);
		
		quantidadeCategoriasCadastradas = (int) categoriaRepository.count();
	}
	

}
