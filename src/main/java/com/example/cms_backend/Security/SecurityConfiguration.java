package com.example.cms_backend.Security;

import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Security.Jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserRepository userRepository;

    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configure(httpSecurity)) // Ensure CORS is enabled
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for API calls
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, "/user").permitAll();
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/admin/login").permitAll();
                    authorize.requestMatchers("parish/search").permitAll();
                    authorize.requestMatchers("/parishes").permitAll();
                    authorize.requestMatchers("/group/search").permitAll();
                    authorize.requestMatchers("/user/profile-picture/").permitAll();
                    authorize.requestMatchers("/img/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/user/*/upload-profile-picture").permitAll();
                    authorize.requestMatchers(HttpMethod.PUT, "/user/*").permitAll();
                    authorize.requestMatchers("/user").permitAll();
                    authorize.requestMatchers("/parish/favorite").permitAll();
                    authorize.requestMatchers("/user/*/favorite-parishes").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter authenticationJwtFilter()  {
        return new JwtAuthenticationFilter(userRepository);
    }
}
