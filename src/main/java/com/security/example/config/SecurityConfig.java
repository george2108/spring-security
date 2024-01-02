package com.security.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// Es una clase de configuración para Spring Security
@Configuration
// Habilita la seguridad web en la aplicación
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(
    HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity
                .authorizeHttpRequests((auth) -> {
                            auth.requestMatchers("/api/v1/index2").permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .formLogin((form) ->
                        form.permitAll()
                                .successHandler(successHandler()) // URL de redirección en caso de éxito cuando se autentica
                )
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Política de creación de sesión
                                .invalidSessionUrl("/login") // URL de redirección en caso de sesión inválida
                                .maximumSessions(1) // Número máximo de sesiones permitidas
                                .expiredUrl("/login") // URL de redirección en caso de sesión expirada
                                .sessionRegistry(sessionRegistry()) // Registro de sesiones para el control de sesiones
                )
                .build();
    }

    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> response.sendRedirect("/api/v1/session");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


}
