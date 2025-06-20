package com.albtoc.TableroTareas.infraestructure.security;

import com.albtoc.TableroTareas.infraestructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private FilterJwt filterJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        {
            try {
                httpSecurity
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()));
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(
                                session ->
                                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry.requestMatchers("/auth/**").permitAll()
                                        .anyRequest().authenticated()
                        );
                httpSecurity
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(filterJwt, UsernamePasswordAuthenticationFilter.class);
                return httpSecurity.build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userRepositoryJpa::findByUsername;
    }

    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://localhost:4200/");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
