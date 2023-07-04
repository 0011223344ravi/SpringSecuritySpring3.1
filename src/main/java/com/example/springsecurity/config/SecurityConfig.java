package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        /*UserDetails admin = User.withUsername("Ravi")
                .password(passwordEncoder().encode("Pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("Baba")
                .password(passwordEncoder().encode("Pwd2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);*/
        return new UserInfoUserDetailsService();
    }

  /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/products/welcome","/products/new")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())

                .securityMatcher("/products/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()).
                formLogin(withDefaults());
        return http.build();



    }*/
   @Bean
   public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
       http
               .securityMatcher("/products/welcome").securityMatcher("products/new")
               .authorizeHttpRequests(authorize -> authorize
                       .anyRequest().permitAll()

               );
       return http.build();
   }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/products/all","/products/{id}")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
               ;
        return http.build();
    }
   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
