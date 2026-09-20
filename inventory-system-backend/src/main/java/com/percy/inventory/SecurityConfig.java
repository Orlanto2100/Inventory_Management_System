package com.percy.inventory;

import com.percy.inventory.Users.CustomUserDetailsService;
import com.percy.inventory.Users.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .userDetailsService(customUserDetailsService)

                .authorizeHttpRequests(auth -> auth

                        // Authentication
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // Administration
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        // Purchasing
                        .requestMatchers("/api/vendors/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )

                        .requestMatchers("/api/rfqs/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )

                        .requestMatchers("/api/purchase-orders/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )

                        // Sales
                        .requestMatchers("/api/customers/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )

                        .requestMatchers("/api/sales-orders/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )

                        // Warehouse / Inventory
                        .requestMatchers("/api/inventory/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers("/api/warehouses/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers("/api/locations/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers("/api/stock-movements/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}