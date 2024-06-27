package br.com.bdws.start_spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static br.com.bdws.start_spring.config.security.Authorities.ADMIN;
import static br.com.bdws.start_spring.config.security.Authorities.EDITOR;

@Configuration
public class InMemoryAuthenticationConfig {

    @Bean
    public UserDetailsService users() {
        // Is not recommended to use UserBuilder in production
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("editor")
                .password("editor")
                .roles(EDITOR.name())
                .build();
        UserDetails admin = users
                .username("admin")
                .password("admin")
                .roles(ADMIN.name(), EDITOR.name())
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
}