package br.com.servicos.servicosApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.servicos.servicosApi.infrastructure.repository.CustomJpaRepositoryImpl;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ServicosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicosApiApplication.class, args);
	}

}
