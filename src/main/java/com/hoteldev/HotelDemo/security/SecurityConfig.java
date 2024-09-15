package com.hoteldev.HotelDemo.security;

import com.hoteldev.HotelDemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ClassName: SecurityConfig
 * Package: com.hoteldev.HotelDemo.security
 * Description:
 *
 * @Author MegaSwampert
 * @Create 5/09/2024 5:44 pm
 * @Version 1.0
 */

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private  CustomUserDetailsService customUserDetailsService;

    @Autowired
    private  JWTAuthFilter jwtAuthFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /*Disables csrf protection: it's not necessary for stateless REST API that
        * use token(JWT) instead of cookies*/
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                /*enable cors with default settings*/
                .cors(Customizer.withDefaults())
                /*allows unauthenticated access to "/auth/**", "/rooms/**", "/bookings/**" endpoints.
                * requires authentication for all others requests*/
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**", "/rooms/**", "/bookings/**")
                        .permitAll().anyRequest().authenticated())
                /*configures the app to be stateless, meaning it doesn't store session data.
                * we're using JWT*/
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*configures the authentication Provider*/
                .authenticationProvider(authenticationProvider())
                /*to intercept requests and handle JWT authentication before default authentication process*/
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
