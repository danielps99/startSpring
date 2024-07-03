package br.com.bdws.start_spring.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
    private final String [] EDITOR_URLS_GET = {
            "/api/product",
            "/api/product/{id}"
    };
    private final String [] ADMIN_URLS_ALL_METHODS = EDITOR_URLS_GET;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(userDetailsService, passwordEncoder);
        httpSecurity
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                        "/error",
                        "/h2-console/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, EDITOR_URLS_GET).hasAuthority(Authorities.EDITOR.role())
                .requestMatchers(ADMIN_URLS_ALL_METHODS).hasAuthority(Authorities.ADMIN.role())
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // filtra requisições de login
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager, tokenAuthenticationService), HeaderWriterFilter.class)
                // filtra outras requisições para verificar a presença do JWT no header
            .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),  HeaderWriterFilter.class);

        return httpSecurity.build();
    }

    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}