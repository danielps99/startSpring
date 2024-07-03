package br.com.bdws.start_spring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static br.com.bdws.start_spring.config.security.Authorities.ADMIN;
import static br.com.bdws.start_spring.config.security.Authorities.EDITOR;

@Configuration
public class InMemoryAuthenticationConfig {

    private PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        User.UserBuilder userBuilder = User.builder().passwordEncoder(passwordEncoder::encode);
        inMemoryUserDetailsManager.createUser(userBuilder
                .username("editor")
                .password("editor")
                .roles(EDITOR.name())
                .build());
        inMemoryUserDetailsManager.createUser(userBuilder
                .username("admin")
                .password("admin")
                .roles(ADMIN.name(), EDITOR.name())
                .build());
        return inMemoryUserDetailsManager;
    }
}