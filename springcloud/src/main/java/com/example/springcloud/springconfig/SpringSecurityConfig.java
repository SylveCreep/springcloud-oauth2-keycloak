package com.example.springcloud.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    // @Bean
    // public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity
    // http) {
    // http.authorizeExchange(exchanges ->
    // exchanges.anyExchange().authenticated()).oauth2Login(withDefaults());
    // http.csrf().disable();
    // return http.build();
    // }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
            ReactiveClientRegistrationRepository clientRegistrationRepository) {
        // Authenticate through configured OpenID Provider
        http.oauth2Login();
        // Also logout at the OpenID Connect provider
        http.logout(logout -> logout
                .logoutSuccessHandler(new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));
        // Require authentication for all requests
        http.authorizeExchange().anyExchange().authenticated();

        http.oauth2ResourceServer().jwt();

        // Allow showing /home within a frame
        http.headers().frameOptions().mode(Mode.SAMEORIGIN);
        // Disable CSRF in the gateway to prevent conflicts with proxied service CSRF
        http.csrf().disable();
        return http.build();
    }
}
