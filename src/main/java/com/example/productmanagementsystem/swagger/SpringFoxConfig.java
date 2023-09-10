package com.example.productmanagementsystem.swagger;

import com.example.productmanagementsystem.swagger.plugins.EmailAnnotationPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET,
                        new ArrayList<Response>(List.of(
                                new ResponseBuilder().code("204").description("No Content").build(),
                                new ResponseBuilder().code("401").description("Unauthorized").build())
                        ))
                .globalResponses(HttpMethod.POST,
                        new ArrayList<Response>(List.of(
                                new ResponseBuilder().code("208").description("Already reported").build(),
                                new ResponseBuilder().code("400").description("Bad request").build(),
                                new ResponseBuilder().code("401").description("Unauthorized").build())
                        ))
                .globalResponses(HttpMethod.PUT,
                        new ArrayList<Response>(List.of(
                                new ResponseBuilder().code("400").description("Bad request").build(),
                                new ResponseBuilder().code("401").description("Unauthorized").build(),
                                new ResponseBuilder().code("403").description("Forbidden").build())
                        ))
                .globalResponses(HttpMethod.DELETE,
                        new ArrayList<Response>(List.of(
                                new ResponseBuilder().code("400").description("Bad request").build(),
                                new ResponseBuilder().code("401").description("Unauthorized").build(),
                                new ResponseBuilder().code("403").description("Forbidden").build())
                        ))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.productmanagementsystem.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Product Management System API",
                "System that allows users to register and login with their credentials, in order to view, add, edit and delete products according to their role (user/admin).",
                "PMS API 1.0",
                null,
                new Contact("Dimitrios Papadogiannakis", null, "papadogd@gmail.com"),
                null, null, Collections.emptyList());
    }

    @Bean
    public EmailAnnotationPlugin emailPlugin() {
        return new EmailAnnotationPlugin();
    }


}
