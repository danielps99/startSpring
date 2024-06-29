package br.com.bdws.start_spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
//
//    @Autowired
////    private TokenAuthenticationService tokenAuthenticationService;
//
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/home", "/register/**", "/authenticate").permitAll();
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.anyRequest().authenticated();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }



//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.headers().frameOptions().disable();
//        httpSecurity.csrf().disable().authorizeRequests()
//                .antMatchers(
//                        "/h2-console/**",
//                        "/swagger-ui/**",
//                        "/v3/api-docs/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/produto").hasAuthority(EDITOR.name())
//                .antMatchers(HttpMethod.GET, "/api/produto/{id}").hasAuthority(EDITOR.name())
//                .antMatchers(HttpMethod.POST, "/api/produto").hasAuthority(ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/produto/{id}").hasAuthority(ADMIN.name())
//                .anyRequest().authenticated()
//                .and()
//                // filtra requisições de login
//                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), tokenAuthenticationService),
//                        UsernamePasswordAuthenticationFilter.class)
//                // filtra outras requisições para verificar a presença do JWT no header
//                .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),
//                        UsernamePasswordAuthenticationFilter.class);
//    }
//

}