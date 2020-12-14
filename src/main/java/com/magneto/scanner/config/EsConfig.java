package com.magneto.scanner.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = { "com.magneto.scanner.repository" })
@ComponentScan(basePackages = { "com.magneto.scanner" })
public class EsConfig {
        // We’ll add some beans here in future.
}