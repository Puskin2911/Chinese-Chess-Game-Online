package com.doubleat.ccgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doubleat.ccgame"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointInfo());

        docket.globalResponseMessage(RequestMethod.GET, Arrays.asList(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Server error")
                        .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden")
                        .build()
        ));

        docket.globalResponseMessage(RequestMethod.POST, Collections.singletonList(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Server error")
                        .build()
        ));

        docket.globalResponseMessage(RequestMethod.PUT, Collections.singletonList(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Server error")
                        .build()
        ));
        docket.globalResponseMessage(RequestMethod.DELETE, Collections.singletonList(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Server error")
                        .build()
        ));

        return docket;
    }

    private ApiInfo apiEndPointInfo() {
        return new ApiInfoBuilder()
                .title("Chinese Chess Game online APIs")
                .description("Chinese Chess Game online")
                .contact(new Contact("Hop Nguyen", "https://doubleat.com/ccgame", "nguyenthehop2000@gmail.com"))
                .version("1.0.0")
                .build();
    }

}
