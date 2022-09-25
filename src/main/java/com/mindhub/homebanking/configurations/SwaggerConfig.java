package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.mindhub.homebanking.controllers";
    private static final String TITLE = "MINDHUB BROTHERS";
    private static final String DESCRIPTION = "MINDHUB BROTHERS Homebanking Application";
    private static final String VERSION = "1.0";
    private static final String CONTACT = "matiascevini@gmail.com";
    private static final String LICENSE = "Public";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/*"))
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .build()
                .apiInfo(new ApiInfo(
                        TITLE,
                        DESCRIPTION,
                        VERSION,
                        "",
                        CONTACT,
                        LICENSE,
                        ""
                ));
    }

}
