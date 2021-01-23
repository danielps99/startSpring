package br.com.br.startSpring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static br.com.br.startSpring.config.security.Authorities.ADMIN;
import static br.com.br.startSpring.config.security.Authorities.EDITOR;

@Configuration
public class InMemoryAuthenticationConfig {

    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                //SENHA admin
                .withUser("admin").password("$2a$10$UqIm1dQEIyNNdbg37y280uTueU6X7H8vaQVXlA5ucJ7Cx1d9EdNuK").authorities(ADMIN.name(), EDITOR.name())
                .and()
                //SENHA editor
                .withUser("editor").password("$2a$10$EpUrB/tkR7V6U07NsZdSp.o841A7ZcSHv3VECVEYhZvfCpBLDBCBa").authorities(EDITOR.name());
    }
}