package com.keshav.student_performance_tracker.config;

import com.keshav.student_performance_tracker.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll() // Allow home and static resources
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                        .requestMatchers("/api/participations").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/participations").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/evaluations/**").permitAll()
                        .requestMatchers("/api/evaluations/**").hasRole("TEACHER")
                        .anyRequest().authenticated()
                );
          http
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .httpBasic(customizer -> {});
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
