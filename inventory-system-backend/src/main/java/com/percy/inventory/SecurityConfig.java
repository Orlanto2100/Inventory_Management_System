package com.percy.inventory;

import com.percy.inventory.Users.auth.CustomUserDetailsService;
import com.percy.inventory.Users.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
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

                        // ==================================================
                        // Authentication
                        // ==================================================

                        .requestMatchers("/api/auth/**")
                        .permitAll()


                        // ==================================================
                        // Administration
                        // Admin only
                        // ==================================================

                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/roles-permissions/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/audit-log/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/system-settings/**")
                        .hasRole("ADMIN")


                        // ==================================================
                        // Products
                        // All company employees
                        // ==================================================

                        .requestMatchers("/api/products/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF",
                                "PURCHASING_STAFF",
                                "SALES_STAFF"
                        )


                        // ==================================================
                        // Purchase Requests
                        // Admin + Warehouse Staff + Purchasing Staff
                        // ==================================================

                        .requestMatchers("/api/purchase-requests/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF",
                                "PURCHASING_STAFF"
                        )


                        // ==================================================
                        // Purchasing
                        // Purchasing Staff + Admin
                        // ==================================================

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

                        .requestMatchers("/api/vendor-quotations/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )

                        .requestMatchers("/api/purchase-orders/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )

                        .requestMatchers("/api/receipts/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )

                        .requestMatchers("/api/vendor-invoices/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )


                        // ==================================================
                        // Sales
                        // Sales Staff + Admin
                        // ==================================================

                        .requestMatchers("/api/customers/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )

                        .requestMatchers("/api/customer-quotations/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )

                        .requestMatchers("/api/sales-orders/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )

                        .requestMatchers("/api/deliveries/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )

                        .requestMatchers("/api/customer-invoices/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )


                        // ==================================================
                        // Warehouse / Inventory
                        // ==================================================

                        .requestMatchers("/api/inventory/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )


                        // ==================================================
                        // Warehouses
                        //
                        // GET:
                        // Admin + Warehouse Staff
                        //
                        // POST:
                        // Admin only
                        //
                        // PATCH:
                        // Admin only
                        //
                        // DELETE:
                        // Admin only
                        // ==================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/warehouses/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/warehouses/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/warehouses/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/warehouses/**"
                        )
                        .hasRole("ADMIN")


                        // ==================================================
                        // Locations
                        //
                        // GET:
                        // Admin + Warehouse Staff
                        //
                        // POST:
                        // Admin only
                        //
                        // PATCH:
                        // Admin only
                        //
                        // DELETE:
                        // Admin only
                        // ==================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/locations/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/locations/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/locations/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/locations/**"
                        )
                        .hasRole("ADMIN")


                        // ==================================================
                        // Warehouse Operations
                        // Warehouse Staff + Admin
                        // ==================================================

                        .requestMatchers("/api/putaways/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers("/api/pickings/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )

                        .requestMatchers("/api/stock-movements/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )


                        // ==================================================
                        // Reports
                        // ==================================================

                        // Inventory Reports
                        // All company employees

                        .requestMatchers("/api/reports/inventory/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF",
                                "PURCHASING_STAFF",
                                "SALES_STAFF"
                        )


                        // Sales Reports
                        // Sales Staff + Admin

                        .requestMatchers("/api/reports/sales/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SALES_STAFF"
                        )


                        // Purchasing Reports
                        // Purchasing Staff + Admin

                        .requestMatchers("/api/reports/purchasing/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PURCHASING_STAFF"
                        )


                        // Operations Reports
                        // Warehouse Staff + Admin

                        .requestMatchers("/api/reports/operations/**")
                        .hasAnyRole(
                                "ADMIN",
                                "WAREHOUSE_STAFF"
                        )


                        // ==================================================
                        // Everything else
                        // ==================================================

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    // ==================================================
    // Authentication Manager
    // ==================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }


    // ==================================================
    // Password Encoder
    // ==================================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}