package com.apis.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.apis.blog.security.CustomUserDetailService;
import com.apis.blog.security.JwtAuthenticationEntryPoint;
import com.apis.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled =true)
public class SecurityConfig {
		
	   @Autowired
	   private CustomUserDetailService customUserDetailService;
	   
	   @Autowired
	   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	   
	   @Autowired
	   private JwtAuthenticationFilter jwtAuthenticationFilter;
	   
	   @Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {		   
		   http
		   .csrf(csrf -> csrf.disable())
		   .cors(cors -> cors.disable())
		   .authorizeHttpRequests(authz -> 
		   	authz
                .requestMatchers("/v2/api-docs","/api/auth/**","/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest()
                .authenticated())
           .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		   
           http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
   
		   
		   return http.build();
	   }
	   
	   @Bean
	   public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	       AuthenticationManagerBuilder authenticationManagerBuilder =
	               http.getSharedObject(AuthenticationManagerBuilder.class);
	       
	       authenticationManagerBuilder
	           .userDetailsService(this.customUserDetailService) 
	           .passwordEncoder(passwordEncoder());
	       
	       return authenticationManagerBuilder.build();
	   }
	   
	   @Bean
	   public PasswordEncoder passwordEncoder() { 
		   return new BCryptPasswordEncoder();
	   }
}
