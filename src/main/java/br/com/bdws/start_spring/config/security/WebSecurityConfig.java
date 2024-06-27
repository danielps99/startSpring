package br.com.bdws.start_spring.config.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.com.bdws.start_spring.config.security.Authorities.ADMIN;
import static br.com.bdws.start_spring.config.security.Authorities.EDITOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenAuthenticationService tokenAuthenticationService;

    private final UserDetailsService userDetailsService;

//    @Bean FUNCIONANDO
//public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    AuthenticationManager authenticationManager = authenticationManager(userDetailsService, PasswordEncoderFactories.createDelegatingPasswordEncoder());
//    httpSecurity
//            .authorizeHttpRequests(authorize -> authorize
//                    .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/users").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/login").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/login").permitAll())
//            .csrf(csrf -> csrf.disable())
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager, tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
////        .addFilterAfter(new JWTAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
//
//    return httpSecurity.build();
//}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(userDetailsService, PasswordEncoderFactories.createDelegatingPasswordEncoder());

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

        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/login","/api/produto", "/api/produto/{id}", "/error").permitAll())
//                        .requestMatchers(HttpMethod.GET, "/api/produto", "/api/produto/{id}")
//                        .hasAuthority(ADMIN.name()))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .sessionManagement()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager, tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),  UsernamePasswordAuthenticationFilter.class)
                .removeConfigurer(AuthorizeHttpRequestsConfigurer.class);//AuthenticationFilter.class

        return httpSecurity.build();
    }

    private void removeConfigurer(Class<AuthorizeHttpRequestsConfigurer> clazz) {
    }

    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

//
//    @Bean
//    public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }

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
//    @Bean
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
}