package com.magneto.scanner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application-title}") String appTitle,
            @Value("${application-version}") String appVersion,
            @Value("${application-description}") String appDescription,
            @Value("${application-terms}") String appTerms,
            @Value("${application-license-name}") String appLicenseName,
            @Value("${application-license-url}") String appLicenseUrl,
            @Value("${application-contact-name}") String appContactName,
            @Value("${application-contact-email}") String appContactEmail
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(appTitle)
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService(appTerms)
                        .license(new License()
                                .name(appLicenseName)
                                .url(appLicenseUrl))
                        .contact(new Contact()
                                .name(appContactName)
                                .email(appContactEmail))
                );
    }
}
