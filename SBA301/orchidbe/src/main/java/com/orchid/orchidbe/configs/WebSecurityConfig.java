package com.orchid.orchidbe.configs;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.orchid.orchidbe.filters.JwtTokenFilter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                // Authentication endpoints
                .requestMatchers(
                    String.format("%s/auth/login", apiPrefix)
                ).permitAll()

                // Public API endpoints
                .requestMatchers(GET,
                                 String.format("%s/roles/**", apiPrefix),
                                 String.format("%s/categories/**", apiPrefix),
                                 String.format("%s/accounts/**", apiPrefix)
                ).permitAll()

                // Require ADMIN role for POST operations
                .requestMatchers(POST,
                                 String.format("%s/roles/**", apiPrefix),
                                 String.format("%s/categories/**", apiPrefix),
                                 String.format("%s/accounts/register", apiPrefix)
                ).permitAll()
                //.hasAnyRole("ADMIN")

                // Require ADMIN role for PUT operations
                .requestMatchers(PUT,
                                 String.format("%s/roles/**", apiPrefix),
                                 String.format("%s/categories/**", apiPrefix)
                ).permitAll()
                //.hasAnyRole("ADMIN")

                // Require ADMIN role for DELETE operations
                .requestMatchers(DELETE,
                                 String.format("%s/roles/**", apiPrefix),
                                 String.format("%s/categories/**", apiPrefix)
                ).permitAll()
                //.hasAnyRole("ADMIN")

                // Required Authenticated User
                .requestMatchers(GET,
                                 String.format("%s/accounts/me", apiPrefix)
                ).authenticated()

                // Swagger UI and API docs
                .requestMatchers(
                    "/graphiql", "/graphql", "/error",
                    "/v3/api-docs/**", "/v3/api-docs.yaml", "/v3/api-docs/swagger-config",
                    "/swagger-ui/**", "/swagger-ui.html",
                    apiPrefix + "/swagger-ui/**",
                    apiPrefix + "/swagger-ui.html",
                    apiPrefix + "/api-docs/**",
                    "/actuator/**"
                ).permitAll()

                // All other endpoints require authentication
                .anyRequest().authenticated())

            // Add JWT token filter
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow your Vercel domain and localhost for development
        configuration.setAllowedOrigins(Arrays.asList(
            "https://shoppe-git-develop-lcaohoanqs-projects.vercel.app",
            "http://localhost:4000",
            "http://localhost:5173"));

        // Allow common HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));

        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"));

        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Authorization"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}