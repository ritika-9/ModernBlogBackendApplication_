package com.ritika.blog.config;

import com.ritika.blog.security.CustomUserDetailService;
import com.ritika.blog.security.JwtAuthenticationEntryPoint;
import com.ritika.blog.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",           // All authentication endpoints (e.g., login, register)
            "/api/v1/auth/login",
            "/v3/api-docs",              // OpenAPI docs (Swagger)
            "/v2/api-docs",
            "/swagger-resources/**",     // Swagger config
            "/swagger-ui/**",            // Swagger UI
            "/webjars/**"                // Swagger's static files (JS, CSS, etc.)
    };


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // don’t need CSRF protection.

                .authorizeHttpRequests(auth -> auth  // To decide who can access which URLs.
                        .requestMatchers(PUBLIC_URLS).permitAll()  // These URLs should be open for everyone — even people who haven’t logged in.

                        .requestMatchers(HttpMethod.GET, "/**").permitAll()  // <- allow GET on all endpoints

                        // Example: restrict /admin/** to users with ADMIN role
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Only allow users with ROLE_ADMIN for these URLs.

                        // Example: restrict /user/** to users with USER role
                        .requestMatchers("/user/**").hasRole("USER")  // Only allow users with ROLE_USER

                        //.requestMatchers(HttpMethod.GET).permitAll()  // We allow any GET request (like viewing posts) without needing to log in.
                        .anyRequest().authenticated()  // All other requests (like POST, PUT, DELETE) need the user to be logged in.
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // If someone tries to access a protected page without logging in, we handle the error with a custom message.
                        .accessDeniedHandler((request, response, accessDeniedException) -> {  // Handles 403 Forbidden errors when user is authenticated but not allowed
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"error\": \"Forbidden: You don't have permission.\" }");
                        })
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // We use JWT (stateless) tokens, so we don't store user sessions on the server.
                )

                .authenticationProvider(daoAuthenticationProvider())  // We tell Spring where to get user details and how to check passwords.

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // We add our custom filter to read JWT from the request and validate it.

        return http.build();
    }
 /*in short:Here are the public URLs. Everything else needs a valid JWT
     token. Don’t store sessions. Check users using our database.
     Scan tokens before any other check*/


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
