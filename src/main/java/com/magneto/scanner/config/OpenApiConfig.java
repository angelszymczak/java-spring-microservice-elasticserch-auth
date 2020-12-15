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

    private static final String BASE_PACKAGE_URL = "com.magneto.scanner.controllers";
    private static final String VERSION = "v1";

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application-title}") String appTitle,
            @Value("${application-contact-email}") String appContactEmail,
            @Value("${application-contact-name}") String appContactName,
            @Value("${application-description}") String appDescription,
            @Value("${application-terms}") String appTerms,
            @Value("${application-license-name}") String appLicenseName,
            @Value("${application-license-url}") String appLicenseUrl,
            @Value("${application-version}") String appVersion
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(appTitle)
                        .contact(new Contact()
                                .email(appContactEmail)
                                .name(appContactName))
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService(appTerms)
                        .license(new License()
                                .name(appLicenseName)
                                .url(appLicenseUrl))
                );
    }
}
