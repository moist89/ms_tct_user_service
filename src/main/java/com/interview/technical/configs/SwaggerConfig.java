package com.interview.technical.configs;

import com.interview.technical.enums.EMessages;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title(EMessages.SWAGGER_TITLE.getValue())
                        .description(EMessages.SWAGGER_DESCRIPTION.getValue())
                        .version(EMessages.SWAGGER_VERSION.getValue()));
    }
}
