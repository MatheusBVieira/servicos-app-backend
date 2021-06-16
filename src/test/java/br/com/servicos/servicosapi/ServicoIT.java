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
import br.com.servicos.servicosApi.domain.model.Cidade;
import br.com.servicos.servicosApi.domain.model.Estado;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.CategoriaRepository;
import br.com.servicos.servicosApi.domain.repository.CidadeRepository;
import br.com.servicos.servicosApi.domain.repository.EstadoRepository;
import br.com.servicos.servicosApi.domain.repository.ServicoRepository;
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
public class ServicoIT {
	private static final int QUANTIDADE_SERVICOS_PROXIMO = 1;

	private static final int SERVICO_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;

	private String token;
	private Usuario usuarioAdmin;
	private Servico servicoFaxineira;
	private int quantidadeServicosCadastradosLimpeza;

	private Usuario usuarioPrestador;

	private Categoria categoriaLimpeza;

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/servicos";

		databaseCleaner.clearTables();
		prepararDados();
		token = UsuarioTestUtil.pegaTokenAdmin();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarServicosPorCategoriaId() {
		given()
			.param("categoriaId", 1)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeServicos_QuandoConsultarServicosProximos() {
		given()
			.param("categoriaId", categoriaLimpeza.getId())
			.param("latitude", -27.600199504638695)
			.param("longitude", -48.43534402342197)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(QUANTIDADE_SERVICOS_PROXIMO));
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeServicos_QuandoConsultarServicos() {
		given()
			.param("categoriaId", categoriaLimpeza.getId())
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeServicosCadastradosLimpeza));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarServico() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/servico-limpeza-cadastra.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+ token)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarServicoExistente() {
		given()
			.pathParam("servicoId", servicoFaxineira.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{servicoId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("titulo", equalTo(servicoFaxineira.getTitulo()));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarServico() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/correto/servico-limpeza-atualiza.json"))
			.pathParam("servicoId", servicoFaxineira.getId())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.put("/{servicoId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoDeletarServico() {
		given()
			.pathParam("servicoId", servicoFaxineira.getId())
			.accept(ContentType.JSON)
			.header("Authorization", "Bearer "+token)
		.when()
			.delete("/{servicoId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarServicoInexistente() {
		given()
			.pathParam("servicoId", SERVICO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{servicoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		usuarioAdmin = UsuarioUtil.criaAdmin("12345678");
		usuarioRepository.save(usuarioAdmin);
		
		usuarioPrestador = UsuarioUtil.criaPrestador("12345678");
		usuarioRepository.save(usuarioPrestador);

		criaCidadeEstado();
		criaCategorias();
		criaServicos();
		
		quantidadeServicosCadastradosLimpeza = servicoRepository.countByCategoria(categoriaLimpeza);
		
	}

	private void criaServicos() {
		servicoFaxineira = new Servico();
		servicoFaxineira.setCategoria(categoriaLimpeza);
		servicoFaxineira.setDescricao("Trabalho a 20 anos como faxineira");
		servicoFaxineira.setPrestadorServico(usuarioPrestador);
		servicoFaxineira.setTitulo("Serviço de faxineira");
		servicoFaxineira.setDistanciaMaxima(5.0);
		servicoRepository.save(servicoFaxineira);
		
		Servico servicoFaxineira2 = new Servico();
		servicoFaxineira2.setCategoria(categoriaLimpeza);
		servicoFaxineira2.setDescricao("Trabalho a 10 anos como faxineira");
		servicoFaxineira2.setPrestadorServico(usuarioAdmin);
		servicoFaxineira2.setTitulo("Serviço de faxineira");
		servicoFaxineira2.setDistanciaMaxima(15.0);
		servicoRepository.save(servicoFaxineira2);
		
		Servico servicoJardinagem = new Servico();
		servicoJardinagem.setCategoria(categoriaLimpeza);
		servicoJardinagem.setDescricao("Trabalho a 10 anos como jardineiro");
		servicoJardinagem.setPrestadorServico(usuarioAdmin);
		servicoJardinagem.setTitulo("Serviço de jardineiro");
		servicoFaxineira.setDistanciaMaxima(20.0);
		servicoRepository.save(servicoJardinagem);
	}

	private void criaCategorias() {
		categoriaLimpeza = PopulaTestUtils.criaCategoriaLimpeza();
		categoriaRepository.save(categoriaLimpeza);
		
		Categoria categoriaJardineiro = new Categoria();
		categoriaJardineiro.setCategoria("Jardineiro");
		categoriaRepository.save(categoriaJardineiro);
	}

	private void criaCidadeEstado() {
		Estado estadoSC = PopulaTestUtils.criaEstadoSC();
		estadoRepository.save(estadoSC);
		
		Cidade cidadeFloripa = PopulaTestUtils.criaCidadeFloripa(estadoSC);
		cidadeRepository.save(cidadeFloripa);
	}





}
