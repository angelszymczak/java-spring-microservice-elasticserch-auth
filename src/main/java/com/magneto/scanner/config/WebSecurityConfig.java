package com.magneto.scanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${magneto.username}")
    String username;

    @Value("${magneto.password}")
    String password;

    @Value("${magneto.role}")
    String role;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(username)
                .password(String.format("{noop}%s", password))
                .roles(role);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/v1/stats").hasRole(role)
                .antMatchers("/v1/mutant").hasRole(role)
                .and().csrf().disable().headers()
                .frameOptions().disable();
    }

}