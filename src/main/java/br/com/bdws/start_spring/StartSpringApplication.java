package br.com.bdws.start_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class StartSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartSpringApplication.class, args);
	}

	@GetMapping("home")
	public String vai() {
		return "vai"+new Date();
	}

}
