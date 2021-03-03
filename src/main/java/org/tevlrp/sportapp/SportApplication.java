package org.tevlrp.sportapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
public class SportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportApplication.class, args);
	}

}
