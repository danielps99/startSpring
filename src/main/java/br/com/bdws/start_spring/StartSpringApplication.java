package br.com.bdws.start_spring;

import br.com.bdws.start_spring.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@SpringBootApplication
@RestController
public class StartSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartSpringApplication.class, args);
	}

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService myUserDetailService;
	@GetMapping("home")
	public String vai() {
		return "vai"+new Date();
	}

	@GetMapping("/admin/home")
	public String handleAdminHome() {
		return "Welcome to ADMIN home!";
	}

	@GetMapping("/user/home")
	public String handleUserHome() {
		return "Welcome to USER home!";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginForm.username(), loginForm.password()
		));
		if (authentication.isAuthenticated()) {

			HttpHeaders responseHeaders = new HttpHeaders();
//			responseHeaders.setLocation(location);
			String s = "Bearer "+jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
			responseHeaders.set("Authorization", s);
			return new ResponseEntity<>(s, responseHeaders, HttpStatus.CREATED);
		} else {
			throw new UsernameNotFoundException("Invalid credentials");
		}
	}

}
