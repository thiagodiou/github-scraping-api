package br.com.tbla.githubscrapingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private static final String BASE_PACKAGE = "br.com.tbla.githubscrapingapi.controller";
	private static final String API_TITULO = "API - Github Scraping";
	private static final String API_DESCRICAO = "API REST that returns the file count, the total number of lines and the total number of bytes grouped by file extension, of a given public Github repository";
	private static final String API_VERSAO = "1.0.0";
	private static final String CONTATO_NOME = "Thiago Batista Lemos de Araujo";
	private static final String CONTATO_GITHUB = "https://github.com/thiagodiou";
	private static final String CONTATO_EMAIL = "thiago-san@hotmail.com";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(basePackage(BASE_PACKAGE))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(buildApiInfo());
	}
	
	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title(API_TITULO)
				.description(API_DESCRICAO)
				.version(API_VERSAO)
				.contact(new Contact(CONTATO_NOME, CONTATO_GITHUB, CONTATO_EMAIL))
				.build();
	}
}
