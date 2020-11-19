package com.ssafy.chuck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import com.ssafy.chuck.group.controller.GroupController;
import com.ssafy.chuck.user.controller.UserController;

//http://localhost:8888/swagger-ui/
@Configuration
public class SwaggerConfig {
	@Autowired
	private TypeResolver resolver;

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("Chuck")
			.useDefaultResponseMessages(false)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.ssafy.chuck"))
			.paths(PathSelectors.ant("/**"))
			.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Chuck API")
			.description("Chuck API Reference for Developers")
			.termsOfServiceUrl("https://edu.ssafy.com")
			.license("Chuck License")
			.licenseUrl("chuck.help@gmail.com").version("1.0").build();
	}
}
