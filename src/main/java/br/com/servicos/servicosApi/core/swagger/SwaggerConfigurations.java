package br.com.servicos.servicosApi.core.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations implements WebMvcConfigurer {

	@Bean
	public Docket servicosApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.servicos.servicosApi"))
				.paths(PathSelectors.ant("/**")).build()
//				.ignoredParameterTypes(Usuario.class)
				.globalOperationParameters( // adicionar um parametro global para os tokens
						Arrays.asList(new ParameterBuilder().name("Authorization").description("Header para token JWT")
								.modelRef(new ModelRef("string")).parameterType("header").required(false).build()));
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
