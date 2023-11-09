package com.zakharau.financial_transactions.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(enforceUniqueMethods = false)
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Financial operations")
                .version("1.0")
                .contact(
                    new Contact()
                        .email("a.zakharaudev@gmail.com")
                        .name("Andrei Zakharau")
                )
        );
  }
}
