package br.com.br.startSpring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InMemoryAuthenticationConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void inMemoryAuthentication(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                //SENHA admin
                .withUser("admin").password("$2a$10$UqIm1dQEIyNNdbg37y280uTueU6X7H8vaQVXlA5ucJ7Cx1d9EdNuK").roles("ADMIN", "EDITOR")
                .and()
                //SENHA editor
                .withUser("editor").password("$2a$10$EpUrB/tkR7V6U07NsZdSp.o841A7ZcSHv3VECVEYhZvfCpBLDBCBa").roles("EDITOR");
    }
}